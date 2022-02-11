package quiz_chat.elama_quiz.bot_ui.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
@NoArgsConstructor
@Getter
@Scope("prototype")
public class BotOptionController implements Executable {

    protected final String type = "option";
    @Setter
    protected long chatId;
    @Setter
    protected CallbackQuery callbackQuery;

    @Override
    public void execute() {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText("You click query button with data: " + callbackQuery.getData());
    }
}
