package quiz_chat.elama_quiz.model;

import lombok.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import quiz_chat.elama_quiz.entities.Quiz;


import java.util.List;

@Component
@NoArgsConstructor
@Scope("prototype")
@Getter
@Setter
@ToString
public class QuestFrame {
    private Quiz finalQuiz;
    private List<Quiz> nextList;
    private Integer frameGroup;
    private Quiz answerQuiz;
    private Quiz questionQuiz;
    private Boolean checkpoint;

    public boolean isItCheckpoint() {
        return checkpoint;
    }

    // Возвращает любой присутствующий квиз
    public Quiz getPresentQuiz() {
        if(finalQuiz != null) {
            return finalQuiz;
        }
        if(answerQuiz != null) {
            return answerQuiz;
        }
        if(questionQuiz != null) {
            return questionQuiz;
        }
        return null;

    }

}
