package quiz_chat.elama_quiz.bot_ui.command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class RouterOfMessages {
    @Autowired
    protected BotOptionController botOptionController;
    @Autowired
    protected BotCommandController botCommandController;
    @Autowired
    protected BotMessageUnknownController botMessageUnknownController;

    public Executable botMessageRoute(Update botMessageUpdating) {
        if(botMessageUpdating.hasCallbackQuery()) {
            botOptionController.setCallbackQuery(botMessageUpdating.getCallbackQuery());
            botOptionController.setChatId(botMessageUpdating.getCallbackQuery().getMessage().getChatId());
            return botOptionController;
        }

        if(botMessageUpdating.hasMessage()) {
            var messageId = botMessageUpdating.getMessage().getChatId();
            var message = botMessageUpdating.getMessage();
            // TODO прописать сравнение со списком команд, установленных в боте
            if(botMessageUpdating.getMessage().isCommand()) {
                botCommandController.setMessage(message);
                botCommandController.setChatId(messageId);
                return botCommandController;
            } else {
                // если что-то кроме любой команды
                botMessageUnknownController.setChatId(messageId);
                botMessageUnknownController.setMessage(message);
                return botMessageUnknownController;
            }
        }
        return null;
    }
}
