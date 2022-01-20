package quiz_chat.elama_quiz.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="quiz", schema = "public")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class QuizEntity {

    @Id
    @Column(unique = true, nullable = false)
    protected Integer id;

    protected Integer group;

    protected Integer next;

    protected Integer prev;

    protected String describe;

    protected String additional;

    protected String question;

    protected Integer delay;

    @Column(name = "final")
    protected Boolean isFinal;

}
