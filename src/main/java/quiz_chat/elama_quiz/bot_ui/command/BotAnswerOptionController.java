package quiz_chat.elama_quiz.bot_ui.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import quiz_chat.elama_quiz.bot_ui.App;
import quiz_chat.elama_quiz.process.QuestGameplay;

@Component
@NoArgsConstructor
@Getter
@Scope("prototype")
public class BotAnswerOptionController implements Executable {
    @Autowired
    protected QuestGameplay questGameplay;
    @Autowired
    protected App app;

    protected final String type = "option";
    @Setter
    protected long chatId;
    @Setter
    protected CallbackQuery callbackQuery;

    @Override
    public void execute() {
        var startMessageList = questGameplay.makeAnswerFromInlineQuery(callbackQuery, String.valueOf(chatId));
        if(startMessageList.size() > 1) {
            app.onUpdateAsynchronousReceived(startMessageList);
        } else {
            app.onUpdateSynchronousReceived(startMessageList);
        }
    }
}
