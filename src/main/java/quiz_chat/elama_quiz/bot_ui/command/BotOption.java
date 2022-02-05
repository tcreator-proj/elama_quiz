package quiz_chat.elama_quiz.bot_ui.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@Getter
public class BotOption implements Executable{

    protected final String type = "option";

    @Override
    public void process() {

    }
}
