package quiz_chat.elama_quiz.bot_ui.message_entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
@Setter
@Getter
@NoArgsConstructor
@Scope("prototype")
public class SendMessageEntity {
    protected SendMessage sendMessage;
    protected int delay;
}
