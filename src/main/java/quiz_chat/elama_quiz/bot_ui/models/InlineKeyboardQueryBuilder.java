package quiz_chat.elama_quiz.bot_ui.models;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class InlineKeyboardQueryBuilder {
    protected InlineKeyboardMarkup inlineKeyboardMarkup;
    protected List<List<InlineKeyboardButton>> keyboard;
    public InlineKeyboardQueryBuilder() {
        inlineKeyboardMarkup = new InlineKeyboardMarkup();
        keyboard = new ArrayList<>();
    }

    public InlineKeyboardQueryBuilder addLine() {
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        keyboard.add(buttons);
        return this;
    }

    public InlineKeyboardQueryBuilder addButton(int line, InlineKeyboardButton inlineKeyboardButton) {
        keyboard.get(line).add(inlineKeyboardButton);
        return this;
    }

    public InlineKeyboardQueryBuilder addButton(int line, String callbackData, String buttonText) {
        var inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(buttonText);
        inlineKeyboardButton.setCallbackData(callbackData);
        keyboard.get(line).add(inlineKeyboardButton);
        return this;
    }

    public InlineKeyboardMarkup build() {
        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }

}
