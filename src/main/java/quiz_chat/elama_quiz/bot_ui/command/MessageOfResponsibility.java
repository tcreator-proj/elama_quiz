package quiz_chat.elama_quiz.bot_ui.command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class MessageOfResponsibility {
    @Autowired
    protected BotOption botOption;
    @Autowired
    protected BotCommand botCommand;
    @Autowired
    protected BotMessageUnknown botMessageUnknown;

    public Executable botMessageRoute(Update botMessageUpdating) {
        if(botMessageUpdating.hasCallbackQuery()) {
            botOption.setCallbackQuery(botMessageUpdating.getCallbackQuery());
            botOption.setChatId(botMessageUpdating.getCallbackQuery().getMessage().getChatId());
            return botOption;
        }

        if(botMessageUpdating.hasMessage()) {
            var messageId = botMessageUpdating.getMessage().getChatId();
            var message = botMessageUpdating.getMessage();
            if(botMessageUpdating.getMessage().isCommand()) {
                botCommand.setMessage(message);
                botCommand.setChatId(messageId);
                return botCommand;
            } else {
                // если что-то кроме любой команды
                botMessageUnknown.setChatId(messageId);
                botMessageUnknown.setMessage(message);
                return botMessageUnknown;
            }
        }
        return null;
    }
}
