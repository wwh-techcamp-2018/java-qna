package codesquad.web;

import codesquad.domain.*;
import codesquad.exception.AuthorizationException;
import codesquad.exception.NotFoundException;
import codesquad.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/api/questions/{qId}/answers", produces = "application/json")
public class ApiAnswerController {

    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("")
    private Answer create(@PathVariable long qId, @RequestBody Answer answer, HttpSession session) {
        Question q = questionRepository.findById(qId).orElseThrow(NotFoundException::new);
        answer.setWriter(Session.getUser(session));
        answer.setQuestion(q);

        return Result.ok(answerRepository.save(answer));
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable long qId, @PathVariable long id, HttpSession s, @RequestHeader HttpHeaders headers) {
        System.out.println(headers);
        Answer answer = answerRepository.findById(id).orElseThrow(NotFoundException::new);
        User user = Session.getUser(s);
        answer.validateWriter(user);
        answer.delete(user);
        answerRepository.save(answer);

        return Result.ok("{\"answerid\": " + answer.getId() + "}");
    }


    @ExceptionHandler({AuthorizationException.class})
    public ResponseEntity.BodyBuilder authHandling() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED);
    }

}
