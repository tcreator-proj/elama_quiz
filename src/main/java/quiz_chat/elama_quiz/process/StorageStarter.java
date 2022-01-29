package quiz_chat.elama_quiz.process;

import lombok.AllArgsConstructor;
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
    private ApplicationContext applicationContext;
    @Autowired
    private UserStateStorageOperation userStateStorageOperation;
    @Autowired
    private QuestRepository questRepository;
    @Autowired
    private QuestStorageBuilder questStorageBuilder;

    // Создаёт новую запись в хранилище и возвращает объект UserData
    public UserData makeNewUserData(Long userId) {
        UserData newUserData = applicationContext.getBean(UserData.class);
        UserRouteStack newUserRouteStack = applicationContext.getBean(UserRouteStack.class);
        QuestStorageOperation questStorageOperation = applicationContext.getBean(QuestStorageOperation.class);
        QuestFrame startQuestFrame = questStorageOperation.getStartsQuestFrame();

        newUserData.setUserRouteStack(newUserRouteStack);
        newUserData.setRouteIsFinish(false);
        newUserData.setCurrentFrame(startQuestFrame);

        return userStateStorageOperation.putUserData(userId, newUserData);
    }

    public void startStorageBuild() {
        var questList = questRepository.findQuizzesByContentIsNotNullOrderByIdAsc();
        questStorageBuilder.buildQuestStorage(questList);
    }

}
