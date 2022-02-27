package quiz_chat.elama_quiz.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;

@Repository
public class UpdateTravelState {
    @Autowired
    protected EntityManager entityManager;
    @Autowired
    protected TransactionTemplate transactionTemplate;

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
    public void setUserStateRouteById(@Param("id") Long id, @Param("route") int[] route) {
        transactionTemplate.execute(transactionStatus -> {
            entityManager.createQuery("UPDATE TravelState ts SET ts.userRoute = :route WHERE ts.id = :id")
                    .setParameter("id", id)
                    .setParameter("route", route)
                    .executeUpdate();
            transactionStatus.flush();
            return null;
        });
    }


}
