package quiz_chat.elama_quiz.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import quiz_chat.elama_quiz.model.Request;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class RequestController {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Request getStr(@RequestBody Request request) {

        return new Request(request.getName(), request.getCounter() + 1);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value ="/chat", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Request getChat(@RequestBody Request request) {
        return new Request(request.getName(), request.getCounter() + 1);
    }

}
