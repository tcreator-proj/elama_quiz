package quiz_chat.elama_quiz.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import quiz_chat.elama_quiz.repository.QuestRepository;
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
