package quiz_chat.elama_quiz.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import quiz_chat.elama_quiz.model.QuestFrame;
import quiz_chat.elama_quiz.model.UserData;
import quiz_chat.elama_quiz.repository.QuestRepository;
import quiz_chat.elama_quiz.repository.QuestStorageOperation;
import quiz_chat.elama_quiz.repository.UserRouteStack;
import quiz_chat.elama_quiz.repository.UserStateStorageOperation;
import quiz_chat.elama_quiz.storage.utill.QuestStorageBuilder;

@Component
public class StorageStarter {
    @Autowired
    private QuestRepository questRepository;
    @Autowired
    private QuestStorageBuilder questStorageBuilder;

    public void startStorageBuild() {
        var questList = questRepository.findQuizzesByContentIsNotNullOrderByIdAsc();
        questStorageBuilder.buildQuestStorage(questList);
    }

}
