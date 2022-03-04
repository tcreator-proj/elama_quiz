package quiz_chat.elama_quiz.bot_ui.game_process;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import quiz_chat.elama_quiz.bot_ui.enums.Start;
import quiz_chat.elama_quiz.bot_ui.message_entity.MessageEntityMaker;
import quiz_chat.elama_quiz.bot_ui.message_entity.SendMessageEntity;
import quiz_chat.elama_quiz.bot_ui.message_entity.SendMessageList;
import quiz_chat.elama_quiz.model.QuestFrame;
import quiz_chat.elama_quiz.repository.QuestStorageDecorator;
import quiz_chat.elama_quiz.repository.TravelStateDecorator;
import quiz_chat.elama_quiz.bot_ui.storage.KeyboardAnswerPoint;
import quiz_chat.elama_quiz.bot_ui.storage.QuizKeyboardMap;

import java.util.ArrayList;

@Component
@AllArgsConstructor
public class QuizGame {
    protected ApplicationContext app;
    protected QuestStorageDecorator questStorageDecorator;
    protected QuizKeyboardMap quizKeyboardMap;
    protected MessageEntityMaker messageEntityMaker;
    protected KeyboardAnswerPoint keyboardAnswerPoint;
    protected TravelStateDecorator travelStateDecorator;

    public SendMessageList questStarter(Message message) {
        // TODO Будет время написать enum
        var startFrame = questStorageDecorator.getFrame(Start.START_FRAME);
        startFrame // создание нового пользователя и установка ему текущего фрейма
                .ifPresent(frame -> travelStateDecorator.setNewUserStartTravelState(message, frame));

        // создание пула собщений
        var msgPoolCreator = getSendMessageList();
        if(startFrame.isPresent()) {

            SendMessageEntity newMsgEntity = makeMessage(startFrame.get(), message.getChatId());
            msgPoolCreator.add(newMsgEntity);
        }
        return msgPoolCreator;
    }

    public SendMessageList answerChat(Message message) {
        Long chatId = message.getChatId();
        var currentFrame = travelStateDecorator.getCurrentFrame(chatId);
        var nextPoint =
                keyboardAnswerPoint.getIfPresent(currentFrame, message.getText().hashCode());
        // если в текущей клавиатуре нет ссылки, значит пользователь пишет своё.
        int nextGroup = nextPoint.orElse(0);
        var msgPoolCreator = getSendMessageList();
        if(nextGroup != 0) {
            // установка следующего фрейма в базу
            travelStateDecorator.setCurrentFrame(chatId, nextGroup);

            var nextFrame = questStorageDecorator.getFrame(nextGroup);
            if(nextFrame.isPresent()) {
                var nextFrameObj = nextFrame.get();
                // если чекпоинт - добавляем в маршрут
                if(nextFrameObj.isItCheckpoint()) {
                    travelStateDecorator.addCheckpointToRouteList(chatId, nextGroup);
                }
                SendMessageEntity newMsgEntity = makeMessage(nextFrameObj, chatId);
                msgPoolCreator.add(newMsgEntity);
                if(nextFrame.get().getFinalQuiz() != null) {
                    addAllCheckpointToCurrentMessageList(chatId, msgPoolCreator);
                }
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
            return messageEntityMaker.makeAnswerEntity(answer, chatId);
        }

        if(finalQuiz != null) {
            return messageEntityMaker.makeFinalEntity(finalQuiz, chatId);
        }
        return null;
    }

    protected void addAllCheckpointToCurrentMessageList(Long chatId, SendMessageList currentMessageList) {
        int[] checkpointList = travelStateDecorator.getCheckpointList(chatId);

        for(int checkpoint : checkpointList) {
            var frame = questStorageDecorator.getFrame(checkpoint);
            frame.ifPresent(f -> {
                var questionQuiz = f.getQuestionQuiz();
                var msgEntity = messageEntityMaker.makeCheckpointEntity(questionQuiz, chatId);
                currentMessageList.add(msgEntity);
            });
        }
    }

    // методы для тестирования отображения контента

    public SendMessageList test(Message message, int param) {
        var nextFrame = questStorageDecorator.getFrame(param);
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
            var nextFrame = questStorageDecorator.getFrame(digit);
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
