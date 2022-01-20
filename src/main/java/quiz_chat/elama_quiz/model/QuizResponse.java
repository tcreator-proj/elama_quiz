package quiz_chat.elama_quiz.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
@Getter
@Setter
public class QuizResponse {
    protected List<QuizEntity> quizList;
}
