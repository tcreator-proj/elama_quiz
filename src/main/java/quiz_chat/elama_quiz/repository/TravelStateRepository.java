package quiz_chat.elama_quiz.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import quiz_chat.elama_quiz.entities.TravelState;

@Repository
public interface TravelStateRepository extends CrudRepository<TravelState, Long> {
    TravelState save(TravelState state);

    TravelState getTravelStateById(Long id);

    // устанавливает текущий фрейм в базу
    @Modifying
    @Query("UPDATE TravelState ts SET ts.currentFrame = :frame WHERE ts.id = :id")
    void setCurrentFrame(
            @Param("id") Long id,
            @Param("frame") int frame
    );

    // Возвращает маршрут с чекпоинтами на пользователя
    @Query("SELECT ts.userRoute FROM TravelState ts WHERE ts.id = :id")
    int[] getUserStateRouteById(@Param("id") Long id);

    // устанавливает новый маршрут с чекпоинтами для пользователя
    @Modifying
    @Query("UPDATE TravelState ts SET ts.userRoute = :route WHERE ts.id = :id")
    void setUserStateRouteById(
            @Param("id") Long id,
            @Param("route") int[] route
    );

    // возвращает текущий фрейм
    @Query("SELECT ts.currentFrame FROM TravelState ts WHERE ts.id = :id")
    int getCurrentFrame(@Param("id") Long id);


    boolean existsTravelStateById(Long id);
}