package quiz_chat.elama_quiz.repository;

import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import quiz_chat.elama_quiz.entities.Quiz;

import java.util.Stack;

@Component
@NoArgsConstructor
@Scope("prototype")
public class UserRouteStack {
    private final Stack<Quiz> routeStack = new Stack<>();

    public void push(Quiz quiz) {
        routeStack.push(quiz);
    }

    public Quiz peek() {
        return routeStack.peek();
    }

    public Quiz pop() {
        return routeStack.pop();
    }
}
