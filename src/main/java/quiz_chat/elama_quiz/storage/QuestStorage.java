package quiz_chat.elama_quiz.storage;
import org.springframework.stereotype.Component;
import quiz_chat.elama_quiz.model.QuestFrame;
import java.util.concurrent.ConcurrentHashMap;

@Component
public abstract class QuestStorage {
    protected static ConcurrentHashMap<Integer, QuestFrame> storage = new ConcurrentHashMap<>();
}
