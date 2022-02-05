package quiz_chat.elama_quiz.bot_ui;
import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import quiz_chat.elama_quiz.bot_ui.controller.BotController;
import quiz_chat.elama_quiz.entities.TravelState;
import quiz_chat.elama_quiz.repository.TravelStateRepository;

import javax.websocket.SendResult;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class App extends TelegramLongPollingBot {
    @Autowired
    protected BotController botController;

    @Getter
    @Value("${bot.name}") private String botUsername;

    @Getter
    @Value("${bot.token}") private String botToken;

    @Autowired
    protected TravelStateRepository travelStateRepository;

    @Autowired
    protected ApplicationContext applicationContext;

    @Override
    public void onRegister() {
        System.out.println("Построение карты квестов");
        botController.startStorage();
    }

    @Override
    public void onUpdateReceived(Update update) {



//        var id = String.valueOf(update.getMessage().getChatId());

//        var inlineButtons = InlineKeyboardButton.builder();
//        var btn = inlineButtons.text("Новое сообщение").callbackData("1").build();
//        var travelState = applicationContext.getBean(TravelState.class);
//        travelState.setCurrentFrame(10);
//        travelState.setId(update.getMessage().getChatId());
//        travelState.setTheEnd(false);
//        travelState.setUserName(update.getMessage().getChat().getUserName());
//        travelState.setUserNickName(update.getMessage().getChat().getFirstName());
//        travelState.setUserRoute(new int[0]);
//
//        travelStateRepository.save(travelState);
        long id = update.getMessage().getChatId();
        if(travelStateRepository.existsTravelStateById(id)) {

            var travelState = travelStateRepository.getTravelStateById(id);
            ArrayList<Integer> listOfDigits = new ArrayList<>();
            Arrays.stream(travelState.getUserRoute()).forEach(listOfDigits::add);
            listOfDigits.add(new Random().nextInt());
            travelState.setUserRoute(listOfDigits.stream().mapToInt(i -> i).toArray());
            travelStateRepository.save(travelState);
        } else {
            System.out.println(2);
        }

//
//        try {
//
//            execute(btn);
//        } catch (TelegramApiException e) {
//            System.out.println(e.getMessage());
//        }
    }
}