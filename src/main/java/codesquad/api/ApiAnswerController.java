package codesquad.api;

import codesquad.domain.Answer;
import codesquad.domain.User;
import codesquad.dto.AnswerDeleteResult;
import codesquad.exception.UnAuthorizedException;
import codesquad.service.AnswerService;
import codesquad.util.SessionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {
    private static final String RESULT_OK_MESSAGE = "ok";
    @Autowired
    AnswerService answerService;

    @PostMapping("")
    public Answer create(@PathVariable Long questionId, @RequestBody Answer newAnswer, HttpSession session) {
        log.info("new answer : {}", newAnswer);
        User loginUser = SessionUtils.getSessionedUser(session);
        if(loginUser == null) {
            throw new UnAuthorizedException();
        }
        return answerService.create(loginUser, questionId, newAnswer);
    }

    @DeleteMapping("/{id}")
    public AnswerDeleteResult delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        User loginUser = SessionUtils.getSessionedUser(session);
        if(loginUser == null) {
            throw new UnAuthorizedException();
        }
        answerService.delete(loginUser, questionId, id);
        return new AnswerDeleteResult(HttpStatus.OK, RESULT_OK_MESSAGE, id);
    }
}
