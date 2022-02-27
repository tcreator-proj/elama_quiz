package quiz_chat.elama_quiz.bot_ui.message_entity;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import quiz_chat.elama_quiz.bot_ui.enums.ParseModeMD;
import quiz_chat.elama_quiz.bot_ui.models.SendMessageBuilder;
import quiz_chat.elama_quiz.entities.Quiz;
import quiz_chat.elama_quiz.bot_ui.storage.QuizKeyboardMap;

@Component
@NoArgsConstructor
public class MessageEntityMaker {
    @Autowired
    protected ApplicationContext applicationContext;
    @Autowired
    protected QuizKeyboardMap quizKeyboardMap;

    public SendMessageEntity makeQuestionEntity(Quiz question, Long chatId) {
        var sendMessageEntity = getSendMessageEntity();
        var sendMessageBuilder = getSendMessageBuilder();
        var keyboard = quizKeyboardMap.getKeyboard(question.getGroup());

        sendMessageBuilder
                .setChatId(chatId)
                .setText(question.getContent())
                .setReplyMarkup(keyboard.orElse(null));
        sendMessageEntity.setSendMessage(sendMessageBuilder.buildMessage());
        return sendMessageEntity;
    }

    public SendMessageEntity makeAnswerEntity(Quiz answer, Long chatId) {
        var sendMessageEntity = getSendMessageEntity();
        var sendMessageBuilder = getSendMessageBuilder();

        sendMessageBuilder
                .setChatId(chatId)
                .setText(answer.getContent());
        sendMessageEntity.setSendMessage(sendMessageBuilder.buildMessage());
        sendMessageEntity.setDelay(answer.getDelay());
        return sendMessageEntity;
    }

    public SendMessageEntity makeFinalEntity(Quiz finalQuiz, Long chatId) {
        var sendMessageEntity = getSendMessageEntity();
        var sendMessageBuilder = getSendMessageBuilder();
        sendMessageBuilder
                .setChatId(chatId)
                .setText(finalQuiz.getContent())
                .setReplyMarkup(quizKeyboardMap.getReplyKeyboardRemove());
        sendMessageEntity.setSendMessage(sendMessageBuilder.buildMessage());

        return sendMessageEntity;
    }

    public SendMessageEntity makeEntityWithAdditional(Quiz quiz, Long chatId) {
        var sendMessageEntity = getSendMessageEntity();
        var sendMessageBuilder = getSendMessageBuilder();
        var keyboard = quizKeyboardMap.getKeyboard(quiz.getGroup());
        sendMessageBuilder
                .setChatId(chatId)
                .setText(quiz.getAdditional())
                .setParseMode(ParseModeMD.MARKDOWN)
                .setReplyMarkup(keyboard.isPresent() ? keyboard.get() : quizKeyboardMap.getReplyKeyboardRemove());
        sendMessageEntity.setSendMessage(sendMessageBuilder.buildMessage());

        return sendMessageEntity;
    }

    // без клавиатуры
    public SendMessageEntity makeFinalEntityWithAdditional(Quiz quiz, Long chatId) {
        var sendMessageEntity = getSendMessageEntity();
        var sendMessageBuilder = getSendMessageBuilder();
        sendMessageBuilder
                .setChatId(chatId)
                .setText(quiz.getAdditional())
                .setParseMode(ParseModeMD.MARKDOWN)
                .setReplyMarkup(quizKeyboardMap.getReplyKeyboardRemove());
        sendMessageEntity.setSendMessage(sendMessageBuilder.buildMessage());

        return sendMessageEntity;
    }



    protected SendMessageEntity getSendMessageEntity() {
        return applicationContext.getBean(SendMessageEntity.class);
    }

    protected SendMessageBuilder getSendMessageBuilder() {
        return applicationContext.getBean(SendMessageBuilder.class);
    }



}
