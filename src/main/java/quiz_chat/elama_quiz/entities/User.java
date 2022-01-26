package quiz_chat.elama_quiz.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Lob
    @Column(name = "email", nullable = false)
    private String id;

    @Column(name = "date_w_tz", nullable = false)
    private OffsetDateTime dateWTz;

    @Column(name = "phone_number", nullable = false, length = 11)
    private String phoneNumber;

    @Lob
    @Column(name = "business_size", nullable = false)
    private String businessSize;
}