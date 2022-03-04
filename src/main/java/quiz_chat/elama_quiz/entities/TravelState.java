package quiz_chat.elama_quiz.entities;

import com.vladmihalcea.hibernate.type.array.IntArrayType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Entity
@Component
@Scope("prototype")
@TypeDefs({
    @TypeDef(
        name = "int-array",
        typeClass = IntArrayType.class
    )
})
@Table(name = "travel_state")
@Getter
@NoArgsConstructor
@Setter
@ToString
public class TravelState {
    @Id
    @Column(name = "chat_id", nullable = false)
    private Long id;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "user_nick_name")
    private String userNickName;

    @Type( type = "int-array" )
    @Column(name = "user_route", columnDefinition = "integer[]")
    private int[] userRoute;

    @Column(name = "the_end")
    private Boolean theEnd;

    @Column(name = "current_frame")
    private Integer currentFrame;
}