package quiz_chat.elama_quiz.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="quiz")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class QuizEntity {

    @Id
    @GeneratedValue
    @Column(name="ID", unique = true, nullable = false)
    protected int id;

    @GeneratedValue
    protected int group;

    @GeneratedValue
    protected int next;

    @GeneratedValue
    protected int prev;

    @GeneratedValue
    protected String describe;

    @GeneratedValue
    protected String additional;

    @GeneratedValue
    protected String question;

    @GeneratedValue
    protected int delay;

    @GeneratedValue
    @Column(name = "final")
    protected boolean isFinal;

}
