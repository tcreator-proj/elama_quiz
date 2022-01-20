package quiz_chat.elama_quiz.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class QuizRequest {
    protected boolean getAllQuiz;
    protected long dataTime;
    protected long idUser;
    protected String userAgent;
}
