package quiz_chat.elama_quiz.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import quiz_chat.elama_quiz.model.QuizEntity;

import java.util.List;

@Repository
public interface QuestRepo extends CrudRepository<QuizEntity, Integer> {
    List<QuizEntity> findAllByQuestionNotNull();

    List<QuizEntity> findQuizEntitiesByQuestionNotNullOrderByIdAsc();

    QuizEntity findQuizEntityById(int id);
}
