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
        int[] route = travelStateRepository.getUserStateRouteById(id);
        var rLen = route.length;
        int[] newRoute = new int[rLen + 1]; // увеличиваем массив маршрута
        newRoute[rLen] = checkpointNum;
        travelStateRepository.setUserStateRouteById(id, newRoute);
    }

    // Устанавлявает текущий фрейм в базу
    public void setCurrentFrameToRoute(Long id, Integer current) {
        travelStateRepository.setCurrentFrame(id, current);
    }

    public void setCurrentFrame(Long id, int frameNumber) {
        travelStateRepository.setCurrentFrame(id, frameNumber);
    }

    public int getCurrentFrame(Long id) {
        return travelStateRepository.getCurrentFrame(id);
    }
}
