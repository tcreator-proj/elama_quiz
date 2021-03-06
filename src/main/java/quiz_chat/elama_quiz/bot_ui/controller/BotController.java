package quiz_chat.elama_quiz.bot_ui.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import quiz_chat.elama_quiz.process.StorageStarter;

@Component
@AllArgsConstructor
public class BotController {
    private StorageStarter storageStarter;

    public void startStorage() {
        storageStarter.startStorageBuild();
    }

}
