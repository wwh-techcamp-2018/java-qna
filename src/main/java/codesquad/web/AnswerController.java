package codesquad.web;

import codesquad.domain.*;
import codesquad.exception.AuthorizationException;
import codesquad.exception.NotFoundException;
import codesquad.exception.NotLoginException;
import codesquad.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("questions/{qId}/answers")
public class AnswerController {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public AnswerController() {
    }


    @GetMapping("/{id}")
    private String show(@PathVariable long qId, @PathVariable long id){
        return "";
    }

    @PostMapping("")
    private String create(@PathVariable long qId, Answer answer, HttpSession session){
        Question q = questionRepository.findById(qId).orElseThrow(NotFoundException::new);
        answer.setWriter(Session.getUser(session));
        answer.setQuestion(q);
        answerRepository.save(answer);

        return "redirect:/questions/" + qId;
    }

    @DeleteMapping("/{id}")
    private String delete (@PathVariable long qId, @PathVariable long id, HttpSession s){
        Answer a = answerRepository.findById(id).orElseThrow(NotFoundException::new);
        a.validateWriter(Session.getUser(s));
        deleteAnswer(a);

        return "redirect:/questions/" + qId;
    }

    private void deleteAnswer(Answer a) {
        a.setDeleted(true);
        answerRepository.save(a);
    }


    @ExceptionHandler({NotLoginException.class, NotFoundException.class, AuthorizationException.class})
    public String handleNotLogin() {
        return "redirect:/users/login";
    }
}
