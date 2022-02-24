package quiz_chat.elama_quiz.bot_ui.models;

import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@Component
@Scope("prototype")
@NoArgsConstructor
public class SendMessageBuilder {
    private String chatId;
    private String text;
    private String parseMode;
    private ReplyKeyboard replyMarkup;

    public SendMessageBuilder(Long id) {
        chatId = String.valueOf(id);
    }

    public SendMessageBuilder setChatId(Long id) {
        chatId = String.valueOf(id);
        return this;
    }

    public SendMessageBuilder setText(String text) {
        this.text = text;
        return this;
    }

    public SendMessageBuilder setParseMode(String parseMode) {
        this.parseMode = parseMode;
        return this;
    }

    public SendMessageBuilder setReplyMarkup(ReplyKeyboard replyMarkup) {
        this.replyMarkup = replyMarkup;
        return this;
    }

    public SendMessage buildMessage() {
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(replyMarkup);
        sendMessage.setParseMode(parseMode);
        return sendMessage;
    }


}
