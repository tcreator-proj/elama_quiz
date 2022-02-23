package quiz_chat.elama_quiz.process;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import quiz_chat.elama_quiz.repository.QuestRepository;
import quiz_chat.elama_quiz.storage.utill.KeyboardAnswerPointBuilder;
import quiz_chat.elama_quiz.storage.utill.QuestStorageBuilder;
import quiz_chat.elama_quiz.storage.utill.QuizKeyboardMapBuilder;

@Component
@Slf4j
public class StorageStarter {
    @Autowired
    private QuestRepository questRepository;
    @Autowired
    private QuestStorageBuilder questStorageBuilder;
    @Autowired
    private KeyboardAnswerPointBuilder keyboardAnswerPointBuilder;
    @Autowired
    private QuizKeyboardMapBuilder quizKeyboardMapBuilder;

    public void startStorageBuild() {
        var questList = questRepository.findQuizzesByContentIsNotNullOrderByIdAsc();
        // построение квиз коллекции
        questStorageBuilder.buildQuestStorage(questList);
        log.info("Построена карта квеста.");
        // TODO логирование настроить
        //построение квиз коллекции ответа на комманды клавиатуры
        keyboardAnswerPointBuilder.buildKeyboardAnswerPoint(questList);
        log.info("Построена карта ответов.");
        // Построение коллекции клавиатур.
        quizKeyboardMapBuilder.buildQuizKeyboardMap(questList);
        log.info("Сформирована коллеция для обработки клавиатуры.");
    }

}
