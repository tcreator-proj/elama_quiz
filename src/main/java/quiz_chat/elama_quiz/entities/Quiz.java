package quiz_chat.elama_quiz.entities;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "quiz")
public class Quiz {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "next")
    private Integer next;

    @Column(name = "\"group\"")
    private Integer group;

    @Column(name = "prev")
    private Integer prev;

    @Column(name = "describe")
    private String describe;

    @Column(name = "additional")
    private String additional;

    @Column(name = "content")
    private String content;

    @Column(name = "delay" )
    private Integer delay;

    @Column(name = "final")
    private Boolean _final;

    @Column(name = "is_question")
    private Boolean isQuestion;

    @Column(name = "is_answer")
    private Boolean isAnswer;

    @Column(name = "is_option")
    private Boolean isOption;

}