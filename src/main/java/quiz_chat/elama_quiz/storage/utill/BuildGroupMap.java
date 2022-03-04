package quiz_chat.elama_quiz.storage.utill;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import quiz_chat.elama_quiz.entities.Quiz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class BuildGroupMap {
    public HashMap<Integer, ArrayList<Quiz>> getGroupHashMap(@NotNull List<Quiz> quizList) {
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
        return tmpStorage;
    }
}
