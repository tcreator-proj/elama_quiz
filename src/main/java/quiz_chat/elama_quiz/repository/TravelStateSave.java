package quiz_chat.elama_quiz.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import quiz_chat.elama_quiz.entities.TravelState;

@Repository
public interface TravelStateSave extends CrudRepository<TravelState, Long> {
    /**
     * Сохраняет сущность в базе данных и возвращает её
     * @param travelState {@link TravelState}
     */
    @NotNull TravelState save(@NotNull TravelState travelState);
}
