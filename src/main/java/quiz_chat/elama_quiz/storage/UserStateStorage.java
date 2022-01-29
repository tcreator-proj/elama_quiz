package quiz_chat.elama_quiz.storage;

import org.springframework.stereotype.Component;
import quiz_chat.elama_quiz.model.UserData;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public abstract class UserStateStorage {
    protected ConcurrentHashMap<Long, UserData> userStateStorage = new ConcurrentHashMap<Long, UserData>();


}
