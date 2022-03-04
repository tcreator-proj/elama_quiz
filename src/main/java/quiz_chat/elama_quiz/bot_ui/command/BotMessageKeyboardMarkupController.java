package quiz_chat.elama_quiz.bot_ui.command;

import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import quiz_chat.elama_quiz.bot_ui.App;
import quiz_chat.elama_quiz.bot_ui.game_process.QuizGame;


@Component
@NoArgsConstructor
@Scope("prototype")
public class BotMessageKeyboardMarkupController implements Executable {
    @Autowired
    protected App app;
    @Autowired
    protected QuizGame quizGame;

    @Setter
    protected long chatId;
    @Setter
    protected Message message;
    @Override
    public void execute() {
        app.onUpdateAsynchronousReceived(quizGame.answerChat(message));
    }

}
