package quiz_chat.elama_quiz.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import quiz_chat.elama_quiz.entities.TravelState;
import quiz_chat.elama_quiz.model.QuestFrame;

@Component
public class TravelStateOperation {
    @Autowired
    protected TravelStateRepository travelStateRepository;
    @Autowired
    protected ApplicationContext applicationContext;
    @Autowired
    protected UpdateTravelState updateTravelState;

    // создаёт начальный объект TravelState и пушит его в базу
    public void setNewUserStartTravelState(Message message, QuestFrame questFrame) {
        TravelState travelState = applicationContext.getBean(TravelState.class);
        travelState.setTheEnd(false);
        travelState.setUserRoute(new int[] {});
        travelState.setUserNickName(message.getChat().getFirstName());
        travelState.setId(message.getChatId());
        travelState.setCurrentFrame(questFrame.getFrameGroup());
        travelState.setUserName(message.getChat().getUserName());
        travelStateRepository.save(travelState);
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
        return travelStateRepository.getUserStateRouteById(id);
    }

    public int[] getUserStateRoute(Long id) {
        var travelState = travelStateRepository.getTravelStateById(id);
        return travelState.getUserRoute();
    }
}
