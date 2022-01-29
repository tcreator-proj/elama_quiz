package quiz_chat.elama_quiz.bot_ui;
import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import quiz_chat.elama_quiz.bot_ui.controller.BotController;

@Component
public class App extends TelegramLongPollingBot {
    @Autowired
    protected BotController botController;

    @Getter
    @Value("${bot.name}") private String botUsername;

    @Getter
    @Value("${bot.token}") private String botToken;

    @Override
    public void onRegister() {
        System.out.println("Построение карты квестов");
        botController.startStorage();
    }

    @Override
    public void onUpdateReceived(Update update) {



        var newPlan = botController.makingNewUserData(update.getMessage().getChatId());
        System.out.println(newPlan.getCurrentFrame().getFrameGroup());
//        try {
//            var msgBuilder = SendMessage.builder();
//            msgBuilder
//                    .chatId(String.valueOf(update.getMessage().getChatId()))
//                    .text("Твфыавафв");
//            execute(msgBuilder.build());
//            execute(msgBuilder.build());
//        } catch (TelegramApiException e) {
//            System.out.println(e.getMessage());
//        }
    }
}