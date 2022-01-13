package quiz_chat.elama_quiz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/")
public class PageController {


    @GetMapping()
    public String getMain() {
        return "chat.html";
    }

    @GetMapping("/chat")
    public String getIndex() {
        return "chat.html";
    }


    @GetMapping("/admin")
    public String getAdmin() {
        return "admin.html";
    }
}
