package quiz_chat.elama_quiz.repository;

import org.springframework.stereotype.Component;
import quiz_chat.elama_quiz.model.QuestFrame;
import quiz_chat.elama_quiz.storage.QuestStorage;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

@Component
public class QuestStorageDecorator extends QuestStorage {
    public Optional<QuestFrame> getFrame(int id) {
        return Optional.of(storage.get(id));
    }

}
