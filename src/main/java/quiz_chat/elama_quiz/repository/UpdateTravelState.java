package quiz_chat.elama_quiz.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import quiz_chat.elama_quiz.entities.TravelState;

import javax.persistence.EntityManager;

@Repository
public class UpdateTravelState {
    @Autowired
    protected EntityManager entityManager;
    @Autowired
    protected TransactionTemplate transactionTemplate;

    /**
     * Сохраняет сущность в базе данных и возвращает её
     * @param state {@link TravelState}
     */
    @Transactional
    @Modifying
    void save(@NotNull TravelState state) {
        transactionTemplate.execute(transactionStatus -> {
            entityManager.createQuery("UPDATE TravelState ts SET ts = :state")
                    .setParameter("state", state)
                    .executeUpdate();
            transactionStatus.flush();
            return null;
        });
    }

    @Transactional
    @Modifying
    public void setCurrentFrame(@Param("id") Long id, @Param("frame") int frame) {
        transactionTemplate.execute(transactionStatus -> {
            entityManager.createQuery("UPDATE TravelState ts SET ts.currentFrame = :frame WHERE ts.id = :id")
                    .setParameter("id", id)
                    .setParameter("frame", frame)
                    .executeUpdate();
            transactionStatus.flush();
            return null;
        });
    }

    @Transactional
    @Modifying
    public void setCheckpoint(@Param("id") Long id, @Param("route") int newCheckpoint) {
        transactionTemplate.execute(transactionStatus -> {
            entityManager
                    .createQuery("UPDATE TravelState ts SET ts.userRoute = " +
                            "array_append(ts.userRoute, :new_checkpoint) WHERE ts.id = :id")
                    .setParameter("id", id)
                    .setParameter("new_checkpoint", newCheckpoint)
                    .executeUpdate();
            transactionStatus.flush();
            return null;
        });
    }


}
