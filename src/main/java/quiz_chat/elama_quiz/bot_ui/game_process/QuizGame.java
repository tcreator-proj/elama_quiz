package quiz_chat.elama_quiz.bot_ui.game_process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import quiz_chat.elama_quiz.bot_ui.message_entity.SendMessageEntity;
import quiz_chat.elama_quiz.bot_ui.message_entity.SendMessageList;
import quiz_chat.elama_quiz.repository.QuestStorageOperation;
import quiz_chat.elama_quiz.repository.TravelStateOperation;
import quiz_chat.elama_quiz.storage.KeyboardAnswerPoint;
import quiz_chat.elama_quiz.storage.QuizKeyboardMap;

@Component
public class QuizGame {
//    @Autowired
//    protected TravelStateRepository travelStateRepository;
    @Autowired
    protected QuestStorageOperation questStorageOperation;
    @Autowired
    protected ApplicationContext applicationContext;
    @Autowired
    protected QuizKeyboardMap quizKeyboardMap;
    @Autowired
    protected KeyboardAnswerPoint keyboardAnswerPoint;
    @Autowired
    protected TravelStateOperation travelStateOperation;

    public SendMessageList questStarter(Message message) {
        // TODO Будет время написать enum
        int START_FRAME = 10;
        var startFrame = questStorageOperation.getFrame(START_FRAME);
        startFrame // создание нового пользователя и установка ему текущего фрейма
                .ifPresent(frame -> travelStateOperation.setNewUserStartTravelState(message, frame));

        // создавние пула собщений
        var sendMessageEntity = applicationContext.getBean(SendMessageEntity.class);
        var messagePool = applicationContext.getBean("");


        return null;
    }


}
