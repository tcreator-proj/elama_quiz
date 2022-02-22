package quiz_chat.elama_quiz.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import quiz_chat.elama_quiz.entities.TravelState;

@Repository
public interface TravelStateRepository extends CrudRepository<TravelState, Long> {
    TravelState save(TravelState state);

    TravelState getTravelStateById(Long id);

    boolean existsTravelStateById(Long id);
}