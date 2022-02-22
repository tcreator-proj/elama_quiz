package quiz_chat.elama_quiz.storage;

import org.springframework.stereotype.Component;
import quiz_chat.elama_quiz.model.KeyboardPointerMap;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class KeyboardAnswerPoint {
    // int(hash ответа с чата) : {
    //   int(указатель на следующий фрейм)
    // }

    static protected ConcurrentHashMap<Integer, KeyboardPointerMap> pointerMap = new ConcurrentHashMap<>();

    // возвращает указатель на следующий фрейм на основе текста кнопки
    public Optional<Integer> getIfPresent(Integer frame, Integer hash) {
        Optional<KeyboardPointerMap> keyboardPointerMap = Optional.of(pointerMap.get(frame));
        return keyboardPointerMap.get().getPointer(hash);
    }

}
