package quiz_chat.elama_quiz.bot_ui.command;
import lombok.Getter;

import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import quiz_chat.elama_quiz.bot_ui.App;
import quiz_chat.elama_quiz.bot_ui.controller.BotController;
import quiz_chat.elama_quiz.bot_ui.game_process.QuizGame;
import quiz_chat.elama_quiz.bot_ui.game_process.QuestGameplay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

@Component
@Getter
@NoArgsConstructor
@Scope("prototype")
public class BotCommandController implements Executable {
    @Autowired
    protected App app;
    @Autowired
    protected QuestGameplay questGameplay;
    // TODO убрать после тестов
    @Autowired
    protected BotController botController;
    //
    @Autowired
    protected QuizGame quizGame;

    protected final String type = "command";
    @Setter
    protected long chatId;
    @Setter
    protected Message message;

    @Override
    public void execute() {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));

        if(message.getText().equals("/start")) {
            var startMessage = quizGame.questStarter(message);
            app.onUpdateAsynchronousReceived(startMessage);
        }

        if(message.getText().equals("/newgame")) {

//            app.onUpdateSynchronousReceived(sendMessage);
//            var startMessage = questGameplay.getStartMessage(message, sendMessage);
//            app.onUpdateSynchronousReceived(startMessage);
        }

        //TODO удалить в продакшне
        if(message.getText().startsWith("/test")) {
            var param = message.getText().split(" ")[1];
            app.onUpdateAsynchronousReceived(quizGame.test(message, Integer.parseInt(param)));

        }

        if(message.getText().startsWith("/addit_test")) {
            var arrLen = message.getText().split(" ");
            var param = Arrays.copyOfRange(arrLen, 1, arrLen.length);
            var intParam = new ArrayList<Integer>();
            for (String str : param) {
                intParam.add(Integer.parseInt(str));
            }
            app.onUpdateAsynchronousReceived(quizGame.additionTest(message, intParam));

        }

        if(message.getText().startsWith("/reset_tree")) {
            botController.startStorage();
        }
    }
}
