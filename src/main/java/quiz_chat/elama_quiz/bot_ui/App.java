package quiz_chat.elama_quiz.bot_ui;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import quiz_chat.elama_quiz.repository.QuestRepository;
import quiz_chat.elama_quiz.storage.utill.QuestStorageBuilder;

@Component
public class App extends TelegramLongPollingBot {
    @Autowired
    private QuestStorageBuilder questStorageBuilder;

    @Autowired
    private QuestRepository questRepository;

    @Getter
    @Value("${bot.name}") private String botUsername;

    @Getter
    @Value("${bot.token}") private String botToken;

    @Override
    public void onUpdateReceived(Update update) {
        var listOfQuest = questRepository.findQuizzesByContentIsNotNullOrderByIdAsc();
        questStorageBuilder.buildQuestStorage(listOfQuest);
        System.out.println(false);
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