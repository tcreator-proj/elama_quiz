package quiz_chat.elama_quiz.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "administrators")
public class Admin {
    @Id
    protected String login;

    @Column
    protected String password;
}
