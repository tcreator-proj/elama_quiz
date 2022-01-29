package quiz_chat.elama_quiz.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import quiz_chat.elama_quiz.model.QuestFrame;
import quiz_chat.elama_quiz.model.UserData;
import quiz_chat.elama_quiz.storage.UserStateStorage;

import java.util.Optional;

public class UserStateStorageOperation extends UserStateStorage {
    @Autowired
    protected ApplicationContext applicationContext;

    // Возвращает объект UserData из хранилища
    protected Optional<UserData> getUserData(Long userId) {
        return Optional.of(userStateStorage.get(userId));
    }

    // Создаёт новую запись в хранилище и возвращает объект UserData
    protected UserData makeUserData(Long userId) {
        UserData newUserDate = applicationContext.getBean(UserData.class);
        UserRouteStack newUserRouteStack = applicationContext.getBean(UserRouteStack.class);
        QuestStorageOperation questStorageOperation = applicationContext.getBean(QuestStorageOperation.class);
        QuestFrame startQuestFrame = questStorageOperation.getStartsQuestFrame();

        newUserDate.setUserRouteStack(newUserRouteStack);
        newUserDate.setRouteIsFinish(false);
        newUserDate.setCurrentFrame(startQuestFrame);

        if(userStateStorage.containsKey(userId)) {
            userStateStorage.put(userId, newUserDate);
            return newUserDate;
        }
        return null;
    }

    // удаляет объект из хранилища, возвращает bool
    protected boolean removeUserData(Long userId) {
        var isContains = userStateStorage.containsKey(userId);
        if(!isContains) {
            return false;
        }
        userStateStorage.remove(userId);
        return true;
    }
}
