package quiz_chat.elama_quiz.storage.utill;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import quiz_chat.elama_quiz.bot_ui.models.ReplyKeyboardMarkerBuilder;
import quiz_chat.elama_quiz.entities.Quiz;
import quiz_chat.elama_quiz.storage.QuizKeyboardMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class QuizKeyboardMapBuilder extends QuizKeyboardMap {
    @Autowired
    protected ApplicationContext applicationContext;

    /**
     * Собирает ConcurrentHashMap из списка квизов
     * @param quizList список Quiz
     */
    public void buildQuizKeyboardMap(@NotNull List<Quiz> quizList) {
        // строим промежуточное хранилище по группам(фреймам)
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
        // пересобирает ConcurrentHashMap<GroupInteger, Quiz> в Map<Keyboard>
        repackingMapToKeyboardFrame(tmpStorage);
    }

    protected void repackingMapToKeyboardFrame(HashMap<Integer, ArrayList<Quiz>> quizMap) {
        //обходим начальную мапу
        for(Map.Entry<Integer, ArrayList<Quiz>> mapElements : quizMap.entrySet()) {
            Integer keyValue = mapElements.getKey();
            ArrayList<Quiz> valueList = mapElements.getValue();
            var replyKeyboardMarkerBuilder = applicationContext.getBean(ReplyKeyboardMarkerBuilder.class);
            //Достаём только варианты выбора
            ArrayList<Quiz> optionList = new ArrayList<>();
            for (Quiz q: valueList) {
                if(q.getIsOption() != null && q.getIsOption()) {
                    optionList.add(q);
                }
            }
            // собираем клавиатуру
            for( int i = 0; i < optionList.size(); i++) {
                var currentOption = optionList.get(i);
                replyKeyboardMarkerBuilder.addLine().addButton(i, currentOption.getContent());
            }
            // добавляем клавиатуру под ключ группы в хранилище
            frameKeyboardMarkup.put(keyValue, replyKeyboardMarkerBuilder.build());
        }
    }



}
