package quiz_chat.elama_quiz.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Scope("prototype")
public class KeyboardPointerMap {
    protected ConcurrentHashMap<Long, Integer> pointerMap = new ConcurrentHashMap<>();

    public Optional<Integer> getPointer(Long hash) {
        return Optional.of(pointerMap.get(hash));
    }

}
