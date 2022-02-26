package quiz_chat.elama_quiz.bot_ui.game_process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import quiz_chat.elama_quiz.bot_ui.message_entity.MessageEntityMaker;
import quiz_chat.elama_quiz.bot_ui.message_entity.SendMessageEntity;
import quiz_chat.elama_quiz.bot_ui.message_entity.SendMessageList;
import quiz_chat.elama_quiz.bot_ui.models.SendMessageBuilder;
import quiz_chat.elama_quiz.model.QuestFrame;
import quiz_chat.elama_quiz.repository.QuestStorageOperation;
import quiz_chat.elama_quiz.repository.TravelStateOperation;
import quiz_chat.elama_quiz.storage.KeyboardAnswerPoint;
import quiz_chat.elama_quiz.storage.QuizKeyboardMap;

import java.util.ArrayList;

@Component
public class QuizGame {

    @Autowired
    protected QuestStorageOperation questStorageOperation;
    @Autowired
    protected ApplicationContext applicationContext;
    @Autowired
    protected QuizKeyboardMap quizKeyboardMap;
    @Autowired
    protected MessageEntityMaker messageEntityMaker;


    @Autowired
    protected KeyboardAnswerPoint keyboardAnswerPoint;
    @Autowired
    protected TravelStateOperation travelStateOperation;

    public SendMessageList questStarter(Message message) {
        // TODO Будет время написать enum
        int START_FRAME_NUMBER = 10;
        var startFrame = questStorageOperation.getFrame(START_FRAME_NUMBER);
        startFrame // создание нового пользователя и установка ему текущего фрейма
                .ifPresent(frame -> travelStateOperation.setNewUserStartTravelState(message, frame));

        // создание пула собщений
        var messagePool = applicationContext.getBean(SendMessageList.class);
        if(startFrame.isPresent()) {
            SendMessageEntity newMsgEntity = makeMessage(startFrame.get(), message.getChatId());
            messagePool.add(newMsgEntity);
        }
        return messagePool;
    }

    public SendMessageList answerChat(Message message) {
        var currentFrame = travelStateOperation.getCurrentFrame(message.getChatId());
        var nextPoint = keyboardAnswerPoint.getIfPresent(currentFrame, message.getText().hashCode());
        int nextGroup = nextPoint.orElse(0);
        travelStateOperation.setCurrentFrame(message.getChatId(), nextGroup);
        var nextFrame = questStorageOperation.getFrame(nextGroup);

        var messagePool = applicationContext.getBean(SendMessageList.class);
        if(nextFrame.isPresent()) {
            SendMessageEntity newMsgEntity = makeMessage(nextFrame.get(), message.getChatId());
            messagePool.add(newMsgEntity);
        }
        return messagePool;
    }

    // собирает сообщения из текущего фрейма. Устанавливает новый фрейм в TravelState
    protected SendMessageEntity makeMessage(QuestFrame frame, Long chatId) {
        var question = frame.getQuestionQuiz();
        var answer = frame.getAnswerQuiz();
        var finalQuiz = frame.getFinalQuiz();

        if(question != null) {
            return messageEntityMaker.makeQuestionEntity(question, chatId);
        }

        if(answer != null) {
            //устанавливаем следующий фрейм в текущий фрейм в TravelState
            //travelStateOperation.setCurrentFrameToRoute(chatId, answer.getNext());
            return messageEntityMaker.makeAnswerEntity(answer, chatId);
        }

        if(finalQuiz != null) {
            return messageEntityMaker.makeFinalEntity(finalQuiz, chatId);
        }
        // TODO разобрать как поступать с checkpoint сообщениями
        return null;
    }


    // методы для тестирования отображения контента

    public SendMessageList test(Message message, int param) {
        var nextFrame = questStorageOperation.getFrame(param);

        var messagePool = applicationContext.getBean(SendMessageList.class);
        if(nextFrame.isPresent()) {
            SendMessageEntity newMsgEntity = makeMessage(nextFrame.get(), message.getChatId());
            messagePool.add(newMsgEntity);
        }
        return messagePool;
    }


    public SendMessageList additionTest(Message message, ArrayList<Integer> paramList) {
        var messagePool = applicationContext.getBean(SendMessageList.class);
        for (Integer digit: paramList) {
            var nextFrame = questStorageOperation.getFrame(digit);
            if(nextFrame.isPresent()) {
                SendMessageEntity newMsgEntity = messageEntityMaker.makeEntityWithAdditional(nextFrame.get().getPresentQuiz(), message.getChatId());
                messagePool.add(newMsgEntity);
            }
        }
        return messagePool;
    }


    protected SendMessageEntity makeAdditionalTest(QuestFrame frame, Long chatId) {
        var question = frame.getQuestionQuiz();

        if(question != null) {
            return messageEntityMaker.makeQuestionEntity(question, chatId);
        }
        // TODO разобрать как поступать с checkpoint сообщениями
        return null;
    }



}
