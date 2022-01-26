package quiz_chat.elama_quiz.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "quiz")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "next")
    private Integer next;

    @Column(name = "\"group\"")
    private Integer group;

    @Column(name = "prev")
    private Integer prev;

    @Lob
    @Column(name = "describe")
    private String describe;

    @Lob
    @Column(name = "additional")
    private String additional;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "delay")
    private Integer delay;

    @Column(name = "final")
    private Boolean _final;

    @Column(name = "isQuestion")
    private Boolean isQuestion;

    @Column(name = "isAnswer")
    private Boolean isAnswer;

    @Column(name = "isOption")
    private Boolean isOption;
}