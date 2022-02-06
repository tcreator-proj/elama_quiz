package quiz_chat.elama_quiz.bot_ui.command;
import lombok.Getter;

import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Getter
@NoArgsConstructor
@Scope("prototype")
public class BotCommand implements Executable {

    protected final String type = "command";
    @Setter
    protected long chatId;
    @Setter
    protected Message message;

    @Override
    public SendMessage getExecutive() {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText("You set bot command: " + message.getText());
        return sendMessage;
    }
}
