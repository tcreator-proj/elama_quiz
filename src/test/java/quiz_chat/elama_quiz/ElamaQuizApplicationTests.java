package quiz_chat.elama_quiz;

import com.sun.research.ws.wadl.Application;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import quiz_chat.elama_quiz.repository.QuestRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = Application.class)
@AutoConfigureMockMvc
class ElamaQuizApplicationTests {

    @Test
    void contextLoads() {
//        Assertions.assertTrue(lists.size() > 0);
    }

}
