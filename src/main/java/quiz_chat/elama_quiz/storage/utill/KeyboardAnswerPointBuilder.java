package quiz_chat.elama_quiz.storage.utill;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import quiz_chat.elama_quiz.entities.Quiz;
import quiz_chat.elama_quiz.model.KeyboardPointerMap;
import quiz_chat.elama_quiz.storage.KeyboardAnswerPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class KeyboardAnswerPointBuilder extends KeyboardAnswerPoint {
    @Autowired
    protected ApplicationContext applicationContext;
    @Autowired
    protected BuildGroupMap buildGroupMap;

    /**
     * Собирает ConcurrentHashMap из списка квизов
     * @param quizList список Quiz
     */
    public void buildKeyboardAnswerPoint(@NotNull List<Quiz> quizList) {
        // строим промежуточное хранилище по группам(фреймам)
        var tmpHasMap= buildGroupMap.getGroupHashMap(quizList);
        // пересобирает ConcurrentHashMap<GroupInteger, Quiz> в Map<Keyboard>
        repackingMapToKeyboardAnswerPoint(tmpHasMap);
    }

    protected void repackingMapToKeyboardAnswerPoint(HashMap<Integer, ArrayList<Quiz>> quizMap) {
        //обходим начальную мапу
        for(Map.Entry<Integer, ArrayList<Quiz>> mapElements : quizMap.entrySet()) {
            Integer groupValue = mapElements.getKey();
            ArrayList<Quiz> valueList = mapElements.getValue();
            var keyboardPointerMap = applicationContext.getBean(KeyboardPointerMap.class);
            //Достаём только варианты выбора

            for (Quiz q: valueList) {
                if(q.getIsOption() != null && q.getIsOption()) {
                    var next = q.getNext() == null ? 333333 : q.getNext();
                    keyboardPointerMap.put(q.getContent().hashCode(), next);
                }
            }

            // добавляем хэш со ссылками в хранилище
            pointerMap.put(groupValue, keyboardPointerMap);
        }
    }
}
