package quiz_chat.elama_quiz.bot_ui.command;
import lombok.Getter;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import quiz_chat.elama_quiz.bot_ui.App;
import quiz_chat.elama_quiz.bot_ui.controller.BotController;
import quiz_chat.elama_quiz.bot_ui.game_process.QuizGame;


@Component
@Getter
@NoArgsConstructor
@Scope("prototype")
@Slf4j
public class BotCommandController implements Executable {
    @Autowired
    protected App app;
    @Autowired
    protected BotController botController;
    @Autowired
    protected QuizGame quizGame;

    @Setter
    protected long chatId;
    @Setter
    protected Message message;

    @Override

    public void execute() {
        if(message.getText().startsWith("/start") || message.getText().startsWith("/newgame")) {
            var startMessage = quizGame.questStarter(message);
            app.onUpdateAsynchronousReceived(startMessage);
        }

        if(message.getText().startsWith("/reset_tree")) {
            log.info("resetting tree");
            botController.startStorage();
        }
    }
}
