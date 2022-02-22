package quiz_chat.elama_quiz.bot_ui;
import lombok.Getter;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import quiz_chat.elama_quiz.bot_ui.command.RouterOfMessages;
import quiz_chat.elama_quiz.bot_ui.controller.BotController;
import quiz_chat.elama_quiz.bot_ui.message_entity.SendMessageEntity;
import quiz_chat.elama_quiz.bot_ui.message_entity.SendMessageList;
import quiz_chat.elama_quiz.repository.TravelStateRepository;

@Component
public class App extends TelegramLongPollingBot {
    @Autowired
    protected BotController botController;
    @Autowired
    protected TravelStateRepository travelStateRepository;
    @Autowired
    protected ApplicationContext applicationContext;

    @Getter
    @Value("${bot.name}") private String botUsername;

    @Getter
    @Value("${bot.token}") private String botToken;

    @Override
    public void onRegister() {
        botController.startStorage();
    }

    // Асинхронная отправка сообщения
    // TODO продумать отправку сообщения из другого потока чтобы не занимать главный поток
    @Async
    public void onUpdateAsynchronousReceived(SendMessageList sendMessageList) {
        try {
            System.out.println(sendMessageList.size());
            for(int i = 0; i < sendMessageList.size(); i++) {
                SendMessageEntity entity = sendMessageList.get(i);
                execute(entity.getSendMessage());
                Thread.sleep(entity.getDelay());
            }
        } catch (TelegramApiException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Синхронная отправка сообщения
    public void onUpdateSynchronousReceived(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {

            e.printStackTrace();
        }
    }

    public void onUpdateSynchronousReceived(SendMessageList messageList) {
        try {
            execute(messageList.get(0).getSendMessage());
        } catch (TelegramApiException e) {

            e.printStackTrace();
        }
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        RouterOfMessages messageRouter = applicationContext.getBean(RouterOfMessages.class);
        var executableEntity = messageRouter.botMessageRoute(update);
        executableEntity.execute();
    }

}