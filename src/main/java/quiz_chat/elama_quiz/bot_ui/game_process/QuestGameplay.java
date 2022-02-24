package quiz_chat.elama_quiz.bot_ui.game_process;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import quiz_chat.elama_quiz.bot_ui.message_entity.SendMessageEntity;
import quiz_chat.elama_quiz.bot_ui.message_entity.SendMessageList;
import quiz_chat.elama_quiz.entities.TravelState;
import quiz_chat.elama_quiz.model.InlineKeyboardQueryBuilder;
import quiz_chat.elama_quiz.model.QuestFrame;
import quiz_chat.elama_quiz.repository.QuestStorageOperation;
import quiz_chat.elama_quiz.repository.TravelStateRepository;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Deprecated
 */
@Component
@NoArgsConstructor
public class QuestGameplay {

    @Autowired
    protected TravelStateRepository travelStateRepository;

    @Autowired
    protected QuestStorageOperation questStorageOperation;

    @Autowired
    protected ApplicationContext applicationContext;

    // Формирует начальный объект для записи в базу маршрутов пользователя. Производит запись
    protected void setStartTravelState(Message message, QuestFrame questFrame) {
        TravelState travelState = applicationContext.getBean(TravelState.class);
        travelState.setTheEnd(false);
        travelState.setUserRoute(new int[] {0});
        travelState.setUserNickName(message.getChat().getFirstName());
        travelState.setId(message.getChatId());
        travelState.setCurrentFrame(questFrame.getAnswerQuiz().getNext());
        travelState.setUserName(message.getChat().getUserName());
        travelStateRepository.save(travelState);
    }

    // Формирует стартовое сообщение и возвращает объект SendMessage
    public SendMessage getStartMessage(Message message, SendMessage sendMessage) {
        var startQuestFrame = questStorageOperation.getStartsQuestFrame().getAnswerQuiz();
        sendMessage.setText(startQuestFrame.getContent());
        var nextId = startQuestFrame.getNext();
        var inlineKeyboardMarkupBuilder = new InlineKeyboardQueryBuilder()
                .addLine()
                .addButton(0, String.valueOf(nextId), "старт")
                .build();
        sendMessage.setReplyMarkup(inlineKeyboardMarkupBuilder);
        setStartTravelState(message, questStorageOperation.getStartsQuestFrame());

        return sendMessage;
    }

    // добавление пользователю группы в рут и номер ответа в маршрут прохождения
    protected void setTravelStateRoute(CallbackQuery callbackQuery) {
        var currentFrame = Integer.parseInt(callbackQuery.getData());
        var currentUser = travelStateRepository.getTravelStateById(callbackQuery.getMessage().getChatId());
        var arrayRoute = currentUser.getUserRoute();
        int[] newRoute = new int[arrayRoute.length + 1];
        newRoute[arrayRoute.length] = currentFrame;
        currentUser.setUserRoute(newRoute);
        currentUser.setCurrentFrame(currentFrame);
        travelStateRepository.save(currentUser);
    }

    // формирует сообщение с кнопками или просто сообщение на коллбэк ответ
    public SendMessageList makeAnswerFromInlineQuery(CallbackQuery callbackQuery, String chatId) {
        setTravelStateRoute(callbackQuery);
        System.out.println(callbackQuery.getData());
        var nextFrame = questStorageOperation.getFrame(Integer.parseInt(callbackQuery.getData()));
        // наполняем список сообщений на отправку
        SendMessageList sendMessageList = applicationContext.getBean(SendMessageList.class);

        if(nextFrame.isPresent()) {
            var answer = nextFrame.get().getAnswerQuiz();
            var question = nextFrame.get().getQuestionQuiz();
            var optionList = nextFrame.get().getNextList();
            var optionFinal = nextFrame.get().getFinalQuiz();

            if (question != null) {
                var sendMessage = new SendMessage();
                var sendMessageEntity = applicationContext.getBean(SendMessageEntity.class);
                sendMessage.setChatId(chatId);
                sendMessage.setText(question.getContent());

                // создание клавиатуры
                var queryKeyboardBuilder = new InlineKeyboardQueryBuilder();
                AtomicInteger i = new AtomicInteger(0);
                if (optionList.size() > 0) {
                    optionList.forEach(quiz -> queryKeyboardBuilder
                            .addLine()
                            .addButton(
                                    i.getAndIncrement(), String.valueOf(quiz.getNext()), quiz.getContent()
                            )
                    );
                    sendMessage.setReplyMarkup(queryKeyboardBuilder.build());
                }
                sendMessageEntity.setSendMessage(sendMessage);
                sendMessageEntity.setDelay(0);
                sendMessageList.add(sendMessageEntity);
            } else if(answer != null) {
                var sendMessage = new SendMessage();
                var sendMessageEntity = applicationContext.getBean(SendMessageEntity.class);
                //TODO подумать над отправкой ответа без кнопки и прописать задержки ответа
                sendMessage.setText(answer.getContent());
                sendMessage.setChatId(chatId);
                sendMessageEntity.setSendMessage(sendMessage);
                sendMessageEntity.setDelay(answer.getDelay());
                //обавление в список сущностей сообщения
                sendMessageList.add(sendMessageEntity);
                // переключение на следующий блок
                callbackQuery.setData(String.valueOf(answer.getNext()));
                setTravelStateRoute(callbackQuery);
                // создание следующего сообщения
                var pastAnswerMessage = new SendMessage();
                pastAnswerMessage.setChatId(chatId);
                var pastAnswerSendMessageEntity = applicationContext.getBean(SendMessageEntity.class);
                var nextFramePastAnswer = questStorageOperation.getFrame(Integer.parseInt(callbackQuery.getData()));
                if(nextFramePastAnswer.isPresent()) {
                    var nextQuestionQuiz = nextFramePastAnswer.get().getQuestionQuiz();
                    var optionListPastAnswer = nextFramePastAnswer.get().getNextList();
                    pastAnswerMessage.setText(nextQuestionQuiz.getContent());
                    // создание клавиатуры
                    var queryKeyboardBuilder = new InlineKeyboardQueryBuilder();

                    AtomicInteger i = new AtomicInteger(0);
                    if (optionListPastAnswer.size() > 0) {
                        optionListPastAnswer.forEach(quizPastAnswer -> queryKeyboardBuilder
                                .addLine()
                                .addButton(
                                        i.getAndIncrement(), String.valueOf(quizPastAnswer.getNext()), quizPastAnswer.getContent()
                                ));
                        pastAnswerMessage.setReplyMarkup(queryKeyboardBuilder.build());
                    }

                    pastAnswerSendMessageEntity.setSendMessage(pastAnswerMessage);
                    pastAnswerSendMessageEntity.setDelay(0);
                    sendMessageList.add(pastAnswerSendMessageEntity);
                }

            }
//            } else if(answer != null && optionFinal != null) {
//                sendMessage.setText(answer.getContent());
//                queryKeyboardBuilder.addLine().addButton(0, null, optionFinal.getContent());
//                sendMessage.setReplyMarkup(queryKeyboardBuilder.build());
//            }

        }
        return sendMessageList;
    }

}
