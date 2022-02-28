package quiz_chat.elama_quiz.repository;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import quiz_chat.elama_quiz.entities.TravelState;
import quiz_chat.elama_quiz.model.QuestFrame;

/**
 * Декоратор для {@link TravelStateRepository} и {@link UpdateTravelState}
 */
@Component
@AllArgsConstructor
public class TravelStateDecorator {
    protected TravelStateRepository travelStateRepository;
    protected ApplicationContext applicationContext;
    protected UpdateTravelState updateTravelState;
    protected TravelStateSave travelStateSave;

    // создаёт начальный объект TravelState и пушит его в базу
    public void setNewUserStartTravelState(Message message, QuestFrame questFrame) {
        TravelState travelState = applicationContext.getBean(TravelState.class);
        var userName = message.getChat().getUserName() != null ? message.getChat().getUserName() : "";
        travelState.setTheEnd(false);
        travelState.setUserRoute(new int[] {});
        travelState.setUserNickName(message.getChat().getFirstName());
        travelState.setId(message.getChatId());
        travelState.setCurrentFrame(questFrame.getFrameGroup());
        travelState.setUserName(userName);
        travelStateSave.save(travelState);
    }

    // добавить новый фрейм в список прохождения пользователя
    public void addCheckpointToRouteList(Long id, Integer checkpointNum) {
        updateTravelState.setCheckpoint(id, checkpointNum);
    }

    // устанавливает номер фрейма, как текущий
    public void setCurrentFrame(Long id, int frameNumber) {
        updateTravelState.setCurrentFrame(id, frameNumber);
    }

    // получает текущий фрейм пользователя
    public int getCurrentFrame(Long id) {
        return travelStateRepository.getCurrentFrame(id);
    }

    public int[] getCheckpointList(Long id) {
        var travelState = travelStateRepository.getTravelStateById(id);
        return travelState.getUserRoute();
    }
}
