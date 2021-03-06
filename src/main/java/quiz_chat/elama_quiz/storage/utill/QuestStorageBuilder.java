package quiz_chat.elama_quiz.storage.utill;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import quiz_chat.elama_quiz.entities.Quiz;
import quiz_chat.elama_quiz.model.QuestFrame;
import quiz_chat.elama_quiz.storage.QuestStorage;
import java.util.*;

/**
 * Вспомогательный класс
 * Билдер пересборки коллекции записей Quiz в коллекцию ассоциаций (Map) "numberOfGroup => QuizFrame
 * Требуется для препроцессинга данных
 */
@Component
public class QuestStorageBuilder extends QuestStorage {
    @Autowired
    protected ApplicationContext applicationContext;
    @Autowired
    protected BuildGroupMap buildGroupMap;

    public void buildQuestStorage(@NotNull List<Quiz> quizList) {
        HashMap<Integer, ArrayList<Quiz>> tmpStorage = buildGroupMap.getGroupHashMap(quizList);
        repackingMapToFrame(tmpStorage);
    }

    // пересобирает таблицу списков в таблицу фреймов квестов
    protected void repackingMapToFrame(HashMap<Integer, ArrayList<Quiz>> quizMap) {
        for(Map.Entry<Integer, ArrayList<Quiz>> mapElements : quizMap.entrySet()) {
            Integer keyValue = mapElements.getKey();
            ArrayList<Quiz> valueList = mapElements.getValue();
            Quiz finalQuiz = null;
            Quiz answerQuiz = null;
            Quiz questionQuiz = null;
            boolean checkpoint = false;

            ArrayList<Quiz> optionList = new ArrayList<>();
            for (Quiz q: valueList) {

                if(q.get_final() != null && q.get_final()) {
                    finalQuiz = q;
                }

                if(q.getIsAnswer() != null && q.getIsAnswer()) {
                    answerQuiz = q;
                }

                if(q.getIsQuestion() != null && q.getIsQuestion()) {
                    questionQuiz = q;
                }

                if(q.getIsOption() != null && q.getIsOption()) {
                    optionList.add(q);
                }
                if(!checkpoint && q.getCheckpoint() != null) {
                    checkpoint = true;
                }
            }
            var qf = applicationContext.getBean(QuestFrame.class);
            qf.setCheckpoint(checkpoint);
            qf.setFrameGroup(keyValue);
            qf.setFinalQuiz(finalQuiz);
            qf.setQuestionQuiz(questionQuiz);
            qf.setAnswerQuiz(answerQuiz);
            qf.setNextList(optionList);
            storage.put(keyValue, qf);
        }
    }
}
