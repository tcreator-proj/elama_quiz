package quiz_chat.elama_quiz.bot_ui.message_entity;

import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@NoArgsConstructor
@Scope("prototype")
public class SendMessageList {
    protected List<SendMessageEntity> sendMessageList = new ArrayList<>();

    public int size() {
        return sendMessageList.size();
    }

    public void add(SendMessageEntity message) {
        sendMessageList.add(message);
    }

    public SendMessageEntity get(int index) {
        return sendMessageList.get(index);
    }

}
