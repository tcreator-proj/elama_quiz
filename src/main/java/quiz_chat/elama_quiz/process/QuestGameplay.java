package quiz_chat.elama_quiz.process;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import quiz_chat.elama_quiz.entities.TravelState;
import quiz_chat.elama_quiz.model.InlineKeyboardQueryBuilder;
import quiz_chat.elama_quiz.model.QuestFrame;
import quiz_chat.elama_quiz.repository.QuestStorageOperation;
import quiz_chat.elama_quiz.repository.TravelStateRepository;

@Component
@NoArgsConstructor
public class QuestGameplay {

    @Autowired
    protected TravelStateRepository travelStateRepository;

    @Autowired
    protected QuestStorageOperation questStorageOperation;

    @Autowired
    protected ApplicationContext applicationContext;

    // Формирует начальный объект для записи в базу маршрутов пользователя. Производит запись
    protected void setStartTravelState(Message message, QuestFrame questFrame) {
        TravelState travelState = applicationContext.getBean(TravelState.class);
        travelState.setTheEnd(false);
        travelState.setUserRoute(new int[] {0});
        travelState.setUserNickName(message.getChat().getFirstName());
        travelState.setId(message.getChatId());
        travelState.setCurrentFrame(questFrame.getAnswerQuiz().getNext());
        travelState.setUserName(message.getChat().getUserName());
        travelStateRepository.save(travelState);
    }

    // Формирует стартовое сообщение и возвращает объект SendMessage
    public SendMessage getStartMessage(Message message, SendMessage sendMessage) {
        var startQuestFrame = questStorageOperation.getStartsQuestFrame().getAnswerQuiz();
        sendMessage.setText(startQuestFrame.getContent());
        var nextId = startQuestFrame.getNext();
        var inlineKeyboardMarkupBuilder = new InlineKeyboardQueryBuilder()
                .addLine()
                .addButton(0, String.valueOf(nextId), "старт")
                .build();
        sendMessage.setReplyMarkup(inlineKeyboardMarkupBuilder);
        setStartTravelState(message, questStorageOperation.getStartsQuestFrame());

        return sendMessage;
    }

}
