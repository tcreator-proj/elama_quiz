package quiz_chat.elama_quiz.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name="users")
public class User {

    @Id
    protected String email;

    @Column(nullable = false)
    private String role;

    @Column(name = "date_w_tz", nullable = false)
    private String time;

    @Column(name = "phone_number", nullable = false)
    private String phone;

    @Column(name = "business_size", nullable = false)
    private String business_size;
}
