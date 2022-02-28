package quiz_chat.elama_quiz.bot_ui.command;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@AllArgsConstructor
public class RouterOfMessages {
    protected BotCommandController botCommandController;
    protected BotMessageUnknownController botMessageUnknownController;
    protected BotMessageKeyboardMarkupController botMessageKeyboardMarkupController;

    public Executable botMessageRoute(Update botMessageUpdating) {
        if(botMessageUpdating.hasMessage()) {
            var messageId = botMessageUpdating.getMessage().getChatId();
            var message = botMessageUpdating.getMessage();
            // TODO прописать сравнение со списком команд, установленных в боте
            if(botMessageUpdating.getMessage().isCommand()) {
                botCommandController.setMessage(message);
                botCommandController.setChatId(messageId);
                return botCommandController;
            } else {
                // если не команда, значит обрабатываем как ответ с клавиатуры
                botMessageKeyboardMarkupController.setMessage(message);
                botMessageKeyboardMarkupController.setChatId(messageId);

                return botMessageKeyboardMarkupController;
            }
        }
        return null;
    }
}
