package quiz_chat.elama_quiz.repository;

import org.springframework.stereotype.Component;
import quiz_chat.elama_quiz.model.QuestFrame;
import quiz_chat.elama_quiz.storage.QuestStorage;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

@Component
public class QuestStorageOperation extends QuestStorage {
    public Optional<QuestFrame> getFrame(int id) {
        return Optional.of(storage.get(id));
    }

    public boolean IsFramePresents(int id) {
        return storage.containsKey(id);
    }

    // TODO возможно не понадобится
    public ConcurrentHashMap<Integer, QuestFrame> filter(Predicate<QuestFrame> predicate) {
        return null;
    }

    // возвращает начальный QuestFrame.
    public QuestFrame getStartsQuestFrame() {
        return storage.get(0);
    }

    // если текст сообщения присутствует в одном из опций текущего фрейма, значит true
    public boolean findTextInOptionsContent(int id, String text) {
        var frame = getFrame(id);
        AtomicBoolean isFind = new AtomicBoolean(false);
        frame.ifPresent(thisFrame -> {
            var list = thisFrame.getNextList();
            if(list.size() > 0) {
                list.forEach(quiz -> isFind.set(quiz.getContent().equals(text)));
            }
        });
        return isFind.get();
    }
}
