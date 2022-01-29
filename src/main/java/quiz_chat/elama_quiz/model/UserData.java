package quiz_chat.elama_quiz.model;

import lombok.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import quiz_chat.elama_quiz.repository.UserRouteStack;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Scope("prototype")
public class UserData {
    // TODO инициализируется при добавлении нового пользователя в чат
    protected UserRouteStack userRouteStack;
    protected QuestFrame currentFrame;
    protected boolean routeIsFinish;
}
