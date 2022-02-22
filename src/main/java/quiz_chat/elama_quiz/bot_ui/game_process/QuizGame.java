package quiz_chat.elama_quiz.bot_ui.game_process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import quiz_chat.elama_quiz.bot_ui.models.ReplyKeyboardMarkerBuilder;
import quiz_chat.elama_quiz.repository.QuestStorageOperation;
import quiz_chat.elama_quiz.repository.TravelStateRepository;

@Component
public class QuizGame {
    @Autowired
    protected TravelStateRepository travelStateRepository;

    @Autowired
    protected QuestStorageOperation questStorageOperation;

    @Autowired
    protected ApplicationContext applicationContext;

    // есть ли в текущем фрейме текст кнопки
    public boolean findTextInFrameOptions(Message msg) {
        long chatId = msg.getChatId();
        var currentState = travelStateRepository.getTravelStateById(chatId);
        int thisFrameGroup = currentState.getCurrentFrame();

        return questStorageOperation.findTextInOptionsContent(thisFrameGroup, msg.getText());
    }

    public ReplyKeyboard getTestKeyboardWithText() {
        var builderKeyboard = applicationContext.getBean(ReplyKeyboardMarkerBuilder.class);
//        return builderKeyboard
//                .addLine().addButton(0, "Кнопка 1").addButton(0, "кнопка 2")
//                .addLine().addButton(1, "кнопка 3").build();
//        return builderKeyboard.buildRemove();
        return null;
    }


}
