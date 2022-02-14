package quiz_chat.elama_quiz.bot_ui.command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class RouterOfMessages {
    @Autowired
    protected BotAnswerOptionController botAnswerOptionController;
    @Autowired
    protected BotCommandController botCommandController;
    @Autowired
    protected BotMessageUnknownController botMessageUnknownController;
    @Autowired
    protected BotMessageOfQuizController botMessageOfQuizController;

    public Executable botMessageRoute(Update botMessageUpdating) {
        if(botMessageUpdating.hasCallbackQuery()) {
            botAnswerOptionController.setCallbackQuery(botMessageUpdating.getCallbackQuery());
            botAnswerOptionController.setChatId(botMessageUpdating.getCallbackQuery().getMessage().getChatId());
            return botAnswerOptionController;
        }

        if(botMessageUpdating.hasMessage()) {
            var messageId = botMessageUpdating.getMessage().getChatId();
            var message = botMessageUpdating.getMessage();
            // TODO прописать сравнение со списком команд, установленных в боте
            if(botMessageUpdating.getMessage().isCommand()) {
                botCommandController.setMessage(message);
                botCommandController.setChatId(messageId);
                return botCommandController;
            } else {
                botMessageOfQuizController.setMessage(message);
                botMessageOfQuizController.setChatId(messageId);
                // Если текста сообщения нет в командах текущего фрейма
                if(botMessageOfQuizController.itIsNotMine()) {
                    botMessageUnknownController.setChatId(messageId);
                    botMessageUnknownController.setMessage(message);
                    return botMessageUnknownController;
                } else {
                    // иначе обратывает как кнопку в текущем контексте прохождения
                    return botMessageOfQuizController;
                }
                // если сообщение содержит текст

            }
        }
        return null;
    }
}
