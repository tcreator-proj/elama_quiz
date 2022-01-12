package quiz_chat.elama_quiz.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import quiz_chat.elama_quiz.model.Request;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class Controller {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Request getStr(@RequestBody Request request) {
        return new Request(request.getName());
    }

}
