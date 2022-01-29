package quiz_chat.elama_quiz.repository;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import quiz_chat.elama_quiz.model.UserData;
import quiz_chat.elama_quiz.storage.UserStateStorage;

import java.util.Optional;

@Component
@AllArgsConstructor
public class UserStateStorageOperation extends UserStateStorage {

    protected ApplicationContext applicationContext;

    // Возвращает объект UserData из хранилища
    public Optional<UserData> getUserData(Long userId) {
        return Optional.of(userStateStorage.get(userId));
    }


    public UserData putUserData(long userId, UserData userData) {
        userStateStorage.put(userId, userData);
        return userData;
    }

    // удаляет объект из хранилища, возвращает bool
    public boolean removeUserData(Long userId) {
        var isContains = userStateStorage.containsKey(userId);
        if(!isContains) {
            return false;
        }
        userStateStorage.remove(userId);
        return true;
    }
}
