package quiz_chat.elama_quiz.bot_ui.game_process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import quiz_chat.elama_quiz.bot_ui.enums.Start;
import quiz_chat.elama_quiz.bot_ui.message_entity.MessageEntityMaker;
import quiz_chat.elama_quiz.bot_ui.message_entity.SendMessageEntity;
import quiz_chat.elama_quiz.bot_ui.message_entity.SendMessageList;
import quiz_chat.elama_quiz.model.QuestFrame;
import quiz_chat.elama_quiz.repository.QuestStorageOperation;
import quiz_chat.elama_quiz.repository.TravelStateOperation;
import quiz_chat.elama_quiz.bot_ui.storage.KeyboardAnswerPoint;
import quiz_chat.elama_quiz.bot_ui.storage.QuizKeyboardMap;

import java.util.ArrayList;

@Component
public class QuizGame {
    @Autowired
    protected ApplicationContext app;

    @Autowired
    protected QuestStorageOperation questStorageOperation;
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
        var startFrame = questStorageOperation.getFrame(Start.START_FRAME);
        startFrame // создание нового пользователя и установка ему текущего фрейма
                .ifPresent(frame -> travelStateOperation.setNewUserStartTravelState(message, frame));

        // создание пула собщений
        var msgPoolCreator = getSendMessageList();
        if(startFrame.isPresent()) {

            SendMessageEntity newMsgEntity = makeMessage(startFrame.get(), message.getChatId());
            msgPoolCreator.add(newMsgEntity);
        }
        return msgPoolCreator;
    }

    public SendMessageList answerChat(Message message) {
        var currentFrame = travelStateOperation.getCurrentFrame(message.getChatId());
        var nextPoint =
                keyboardAnswerPoint.getIfPresent(currentFrame, message.getText().hashCode());
        int nextGroup = nextPoint.orElse(0);
        var msgPoolCreator = getSendMessageList();
        if(nextGroup != 0) {
            // установка следующего фрейма в базу
            travelStateOperation.setCurrentFrame(message.getChatId(), nextGroup);

            var nextFrame = questStorageOperation.getFrame(nextGroup);
            if(nextFrame.isPresent()) {

                SendMessageEntity newMsgEntity = makeMessage(nextFrame.get(), message.getChatId());
                msgPoolCreator.add(newMsgEntity);
            }
        }
        return msgPoolCreator;
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
        var msgPoolCreator = getSendMessageList();
        if(nextFrame.isPresent()) {
            SendMessageEntity newMsgEntity = makeMessage(nextFrame.get(), message.getChatId());
            msgPoolCreator.add(newMsgEntity);
        }
        return msgPoolCreator;
    }


    public SendMessageList additionTest(Message message, ArrayList<Integer> paramList) {
        var msgPoolCreator = getSendMessageList();
        for (Integer digit: paramList) {
            var nextFrame = questStorageOperation.getFrame(digit);
            if(nextFrame.isPresent()) {
                SendMessageEntity newMsgEntity = messageEntityMaker.makeEntityWithAdditional(nextFrame.get().getPresentQuiz(), message.getChatId());
                msgPoolCreator.add(newMsgEntity);
            }
        }
        return msgPoolCreator;
    }

    public SendMessageList getSendMessageList() {
        return app.getBean(SendMessageList.class);
    }
}
