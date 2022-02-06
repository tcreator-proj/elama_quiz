package quiz_chat.elama_quiz.bot_ui.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Getter
@Component
@ToString
@NoArgsConstructor
@Scope("prototype")
public class BotMessageUnknown implements Executable {
    protected final String type = "i don't know";
    @Setter
    protected long chatId;
    @Setter
    protected Message message;

    @Override
    public SendMessage getExecutive() {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText("Не могу разобрать, что это за команда" + message.getText() + ". Команды начинаются с \\ ");
        return sendMessage;
    }
}
