package quiz_chat.elama_quiz.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import quiz_chat.elama_quiz.entities.TravelState;

/**
 * Репозиторий для получения данных с TravelState
 */
@Repository
public interface TravelStateRepository extends CrudRepository<TravelState, Long> {


    /**
     * Возвращает запись {@link TravelState}
     * @param id {@link Long} chat_id пользователя
     * @return {@link TravelState} запись прогресса пользователя
     */
    TravelState getTravelStateById(Long id);

    /**
     * Возвращает поле currentFrame по id пользователя
     * @param id {@link Long} chat_id пользователя
     * @return int возвращает число описывающую текущий фрейм прохождения
     */
    @Query("SELECT ts.currentFrame FROM TravelState ts WHERE ts.id = :id")
    int getCurrentFrame(@Param("id") Long id);

}