package quiz_chat.elama_quiz.process;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import quiz_chat.elama_quiz.entities.TravelState;
import quiz_chat.elama_quiz.model.InlineKeyboardQueryBuilder;
import quiz_chat.elama_quiz.model.QuestFrame;
import quiz_chat.elama_quiz.repository.QuestStorageOperation;
import quiz_chat.elama_quiz.repository.TravelStateRepository;

import java.util.concurrent.atomic.AtomicInteger;

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

    // добавление пользователю группы в рут и номер ответа в маршрут прохождения
    protected void setTravelStateRoute(CallbackQuery callbackQuery) {
        var currentFrame = Integer.parseInt(callbackQuery.getData());
        var currentUser = travelStateRepository.getTravelStateById(callbackQuery.getMessage().getChatId());
        var arrayRoute = currentUser.getUserRoute();
        int[] newRoute = new int[arrayRoute.length];
        newRoute[arrayRoute.length - 1] = currentFrame;
        currentUser.setUserRoute(newRoute);
        currentUser.setCurrentFrame(currentFrame);
        travelStateRepository.save(currentUser);
    }

    // формирует сообщение с кнопками или просто сообщение на коллбэк ответ
    public SendMessage makeAnswerFromInlineQuery(CallbackQuery callbackQuery, SendMessage sendMessage) {
        setTravelStateRoute(callbackQuery);
        var nextFrame = questStorageOperation.getFrame(Integer.parseInt(callbackQuery.getData()));
        var queryKeyboardBuilder = new InlineKeyboardQueryBuilder();
        if(nextFrame.isPresent()) {
            var answer = nextFrame.get().getAnswerQuiz();
            var question = nextFrame.get().getQuestionQuiz();
            var optionList = nextFrame.get().getNextList();
            var optionFinal = nextFrame.get().getFinalQuiz();

            if(question != null) {
                sendMessage.setText(question.getContent());
                AtomicInteger i = new AtomicInteger(0);
                if(optionList.size() > 0) {
                    optionList.forEach(quiz -> {
                        queryKeyboardBuilder.addLine().addButton(i.getAndIncrement(), String.valueOf(quiz.getNext()), quiz.getContent());
                    });
                    sendMessage.setReplyMarkup(queryKeyboardBuilder.build());
                }
            }

            if(answer != null) {
                sendMessage.setText(answer.getContent());
                queryKeyboardBuilder.addLine().addButton(0, String.valueOf(answer.getNext()), "продолжить");
                sendMessage.setReplyMarkup(queryKeyboardBuilder.build());
            }

            if(answer != null && optionFinal != null) {
                sendMessage.setText(answer.getContent());
                queryKeyboardBuilder.addLine().addButton(0, null, optionFinal.getContent());
                sendMessage.setReplyMarkup(queryKeyboardBuilder.build());
            }

        }
        return sendMessage;
    }

}
