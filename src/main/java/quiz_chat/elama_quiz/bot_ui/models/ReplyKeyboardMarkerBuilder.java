package quiz_chat.elama_quiz.bot_ui.models;

import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class ReplyKeyboardMarkerBuilder {
    protected ReplyKeyboardMarkup replyKeyboardMarkup;
    protected List<KeyboardRow> keyboard;
    @Setter
    protected boolean resize = true;
    @Setter
    protected boolean singleUse = true;


    public ReplyKeyboardMarkerBuilder() {
        replyKeyboardMarkup = new ReplyKeyboardMarkup();
        keyboard = new ArrayList<>();
    }

    public ReplyKeyboardMarkerBuilder addLine() {
        keyboard.add(new KeyboardRow());
        return this;
    }

    public ReplyKeyboardMarkerBuilder addButton(int line, String buttonText) {
        var keyboardButton = new KeyboardButton();
        keyboardButton.setText(buttonText);
        keyboard.get(line).add(keyboardButton);
        return this;
    }

    public ReplyKeyboardMarkup build() {
        replyKeyboardMarkup.setResizeKeyboard(resize);
        replyKeyboardMarkup.setOneTimeKeyboard(singleUse);
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardRemove buildRemove() {
        ReplyKeyboardRemove replyKeyboardRemove = new ReplyKeyboardRemove();
        replyKeyboardRemove.setRemoveKeyboard(true);
        return replyKeyboardRemove;
    }
}
