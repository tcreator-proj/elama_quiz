package quiz_chat.elama_quiz.storage;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import quiz_chat.elama_quiz.model.QuestFrame;
import java.util.concurrent.ConcurrentHashMap;

@Component
@NoArgsConstructor
public class QuestStorage {
    protected ConcurrentHashMap<Integer, QuestFrame> storage = null;
}
