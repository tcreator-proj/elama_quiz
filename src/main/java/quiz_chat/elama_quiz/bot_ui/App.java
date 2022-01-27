package quiz_chat.elama_quiz.bot_ui;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.inlinequery.ChosenInlineQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class App extends TelegramLongPollingBot {
    @Value("${bot.name}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            var replyMarkup = new ReplyKeyboardMarkup();
            replyMarkup.setInputFieldPlaceholder("Placeholder");
            replyMarkup.setInputFieldPlaceholder("1");

            var message = new SendMessage();
            message.setReplyMarkup(replyMarkup);
            message.setChatId(String.valueOf(update.getMessage().getChatId()));
            message.setText("sd");
            execute(message);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    //Bot body.
}