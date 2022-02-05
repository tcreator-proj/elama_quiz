package quiz_chat.elama_quiz.bot_ui.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@Getter
public class BotCommand implements Executable {

    protected final String type = "command";

    @Override
    public void process() {

    }
}
