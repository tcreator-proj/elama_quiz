package quiz_chat.elama_quiz.bot_ui.command;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@AllArgsConstructor
public class MessageOfResponsibility {
    @Autowired
    protected BotOption botOption;
    @Autowired
    protected BotCommand botCommand;

    public Executable botMessageRoute(Update botMessageUpdating) {
        return null;
    }
}
