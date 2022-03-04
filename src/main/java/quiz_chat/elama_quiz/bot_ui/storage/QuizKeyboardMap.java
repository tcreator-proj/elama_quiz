package quiz_chat.elama_quiz.bot_ui.storage;

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

    // возвращает клавиатуру по номеру фрейма
    public Optional<ReplyKeyboardMarkup> getKeyboard(Integer frame) {
        return Optional.of(frameKeyboardMarkup.get(frame));
    }

    public ReplyKeyboardRemove getReplyKeyboardRemove() {
        var replyKeyboardRemove = new ReplyKeyboardRemove();
        replyKeyboardRemove.setRemoveKeyboard(true);
        return replyKeyboardRemove;
    }
}
