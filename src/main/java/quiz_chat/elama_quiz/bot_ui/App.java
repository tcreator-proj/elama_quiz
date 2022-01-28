package quiz_chat.elama_quiz.bot_ui;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class App extends TelegramLongPollingBot {
    @Getter
    @Value("${bot.name}") private String botUsername;

    @Getter
    @Value("${bot.token}") private String botToken;

    @Override
    public void onUpdateReceived(Update update) {
        try {
            var msgBuilder = SendMessage.builder();
            msgBuilder
                    .chatId(String.valueOf(update.getMessage().getChatId()))
                    .text("Твфыавафв");
            execute(msgBuilder.build());
            execute(msgBuilder.build());
        } catch (TelegramApiException e) {
            System.out.println(e.getMessage());
        }
    }
}