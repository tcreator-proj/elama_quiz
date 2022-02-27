package quiz_chat.elama_quiz.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import quiz_chat.elama_quiz.entities.TravelState;

@Repository
public interface TravelStateRepository extends CrudRepository<TravelState, Long> {
    TravelState save(TravelState state);

    TravelState getTravelStateById(Long id);

    // Возвращает маршрут с чекпоинтами на пользователя
    @Query("SELECT ts.userRoute FROM TravelState ts WHERE ts.id = :id")
    int[] getUserStateRouteById(@Param("id") Long id);

    // возвращает текущий фрейм
    @Query("SELECT ts.currentFrame FROM TravelState ts WHERE ts.id = :id")
    int getCurrentFrame(@Param("id") Long id);


    boolean existsTravelStateById(Long id);
}