package quiz_chat.elama_quiz.bot_ui.command;
import lombok.Getter;

import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import quiz_chat.elama_quiz.bot_ui.App;
import quiz_chat.elama_quiz.process.QuestGameplay;

@Component
@Getter
@NoArgsConstructor
@Scope("prototype")
public class BotCommandController implements Executable {
    @Autowired
    protected App app;
    @Autowired
    protected QuestGameplay questGameplay;

    protected final String type = "command";
    @Setter
    protected long chatId;
    @Setter
    protected Message message;

    @Override
    public void execute() {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));

        if(message.getText().equals("/newgame") || message.getText().equals("/start")) {
            var startMessage = questGameplay.getStartMessage(message, sendMessage);
            app.onUpdateSynchronousReceived(startMessage);
        }
    }
}
