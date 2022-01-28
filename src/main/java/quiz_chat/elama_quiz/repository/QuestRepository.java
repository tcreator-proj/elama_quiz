package quiz_chat.elama_quiz.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import quiz_chat.elama_quiz.entities.Quiz;

import java.util.List;

@Repository
public interface QuestRepository extends CrudRepository<Quiz, Integer> {
    List<Quiz> findQuizzesByContentNotNullOrderByIdAsc();

    List<Quiz> findQuizById(int id);
}
