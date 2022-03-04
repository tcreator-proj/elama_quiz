package quiz_chat.elama_quiz.repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import quiz_chat.elama_quiz.entities.TravelState;
import quiz_chat.elama_quiz.model.QuestFrame;

import java.util.Arrays;

/**
 * Декоратор для {@link TravelStateRepository} и {@link UpdateTravelState}
 */
@Component
@Slf4j
@AllArgsConstructor
public class TravelStateDecorator {
    protected TravelStateRepository travelStateRepository;
    protected ApplicationContext applicationContext;
    protected UpdateTravelState updateTravelState;
    protected TravelStateSave travelStateSave;

    // создаёт начальный объект TravelState и пушит его в базу
    public void setNewUserStartTravelState(Message message, QuestFrame questFrame) {
        try {
            TravelState travelState = applicationContext.getBean(TravelState.class);
            var userName = message.getChat().getUserName() != null ? message.getChat().getUserName() : "";
            travelState.setTheEnd(false);
            travelState.setUserRoute(new int[] {});
            travelState.setUserNickName(message.getChat().getFirstName());
            travelState.setId(message.getChatId());
            travelState.setCurrentFrame(questFrame.getFrameGroup());
            travelState.setUserName(userName);
            // log
            log.info("New user " + travelState);

            travelStateSave.save(travelState);
        } catch (BeansException e) {
            log.error(e.getMessage() + Arrays.toString(e.getStackTrace()));
        }


    }

    // добавить новый фрейм в список прохождения пользователя
    public void addCheckpointToRouteList(Long id, Integer checkpointNum) {
        try {
            updateTravelState.setCheckpoint(id, checkpointNum);
        } catch (Exception e) {
            log.error(e.getMessage() + Arrays.toString(e.getStackTrace()));
        }
    }

    // устанавливает номер фрейма, как текущий
    public void setCurrentFrame(Long id, int frameNumber) {
        try {
            updateTravelState.setCurrentFrame(id, frameNumber);
        } catch (Exception e) {
            log.error(e.getMessage() + Arrays.toString(e.getStackTrace()));
        }
    }

    // получает текущий фрейм пользователя
    public int getCurrentFrame(Long id) {
        try {
            return travelStateRepository.getCurrentFrame(id);
        } catch (Exception e) {
            log.error(e.getMessage() + Arrays.toString(e.getStackTrace()));
        }
        return 0;
    }

    public int[] getCheckpointList(Long id) {
        try {
            return travelStateRepository.getTravelStateById(id).getUserRoute();
        } catch (Exception e) {
            log.error(e.getMessage() + Arrays.toString(e.getStackTrace()));
        }
        return null;
    }
}
