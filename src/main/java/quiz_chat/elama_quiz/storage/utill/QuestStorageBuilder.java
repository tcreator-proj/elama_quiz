package quiz_chat.elama_quiz.storage.utill;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import quiz_chat.elama_quiz.entities.Quiz;
import quiz_chat.elama_quiz.model.QuestFrame;
import quiz_chat.elama_quiz.storage.QuestStorage;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Component
@NoArgsConstructor
public class QuestStorageBuilder extends QuestStorage {
    @Autowired
    protected ApplicationContext applicationContext;

    /**
     * Строит мапу квеста из списка
     * @param quizList список вопросов
     */
    public void buildQuestStorage(List<Quiz> quizList) {
        storage = new ConcurrentHashMap<>();
        HashMap<Integer, ArrayList<Quiz>> tmpStorage = new HashMap<>();
        quizList.forEach(element -> {
            var groupId = element.getGroup();
            if(!tmpStorage.containsKey(groupId)) {
                tmpStorage.put(groupId, new ArrayList<>());
                tmpStorage.get(groupId).add(element);
            } else {
                tmpStorage.get(groupId).add(element);
            }
        });
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
            }
            var qf = applicationContext.getBean(QuestFrame.class);
            qf.setFrameGroup(keyValue);
            qf.setFinalQuiz(finalQuiz);
            qf.setQuestionQuiz(questionQuiz);
            qf.setAnswerQuiz(answerQuiz);
            qf.setNextList(optionList);
            storage.put(keyValue, qf);
        }
    }
}
