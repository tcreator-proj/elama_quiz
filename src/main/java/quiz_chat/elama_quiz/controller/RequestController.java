package quiz_chat.elama_quiz.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import quiz_chat.elama_quiz.model.Administrator;
import quiz_chat.elama_quiz.model.QuizEntity;
import quiz_chat.elama_quiz.model.QuizRequest;

import quiz_chat.elama_quiz.repository.AdminRepository;
import quiz_chat.elama_quiz.repository.QuestRepo;

import java.util.List;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class RequestController {
    private QuestRepo questRepo;

    private AdminRepository adminRepository;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value ="/chat", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<QuizEntity> getAll() {

        return questRepo.findQuizEntitiesByQuestionNotNullOrderByIdAsc();
    }


//    @ResponseStatus(HttpStatus.OK)
//    @PostMapping(value ="/chat", produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseBody
//    public QuizEntity getEntity() {
//        return questRepo.findQuizEntityById(2);
//    }

//    @ResponseStatus(HttpStatus.OK)
//    @PostMapping(value ="/chat", produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseBody
//    public Administrator getAdmin() {
//        return adminRepository.findAdministratorByLogin("god1111");
//    }



}
