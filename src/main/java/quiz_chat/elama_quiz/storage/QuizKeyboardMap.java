package quiz_chat.elama_quiz.storage;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
@NoArgsConstructor
public class QuizKeyboardMap {
    static protected ConcurrentHashMap<Integer, ReplyKeyboardMarkup> frameKeyboardMarkup = new ConcurrentHashMap<>();
    static protected ReplyKeyboardRemove replyKeyboardRemove = new ReplyKeyboardRemove();

    // возвращает клавиатуру по номеру фрейма
    public Optional<ReplyKeyboardMarkup> getKeyboard(Integer frame) {
        return Optional.of(frameKeyboardMarkup.get(frame));
    }

    public ReplyKeyboardRemove getReplyKeyboardRemove() {
        return replyKeyboardRemove;
    }
}
