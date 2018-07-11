package codesquad.web;

import codesquad.domain.Answer;
import codesquad.domain.User;
import codesquad.service.AnswerService;
import codesquad.util.SessionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
    @Autowired
    private AnswerService answerService;

    @PostMapping("")
    public String create(@PathVariable Long questionId, Answer newAnswer, HttpSession session) {
        User loginUser = SessionUtils.getSessionedUser(session);
        if(loginUser == null)
            throw new IllegalArgumentException();

        answerService.create(loginUser, questionId, newAnswer);
        return String.format("redirect:/questions/%d", questionId);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        User loginUser = SessionUtils.getSessionedUser(session);
        if(loginUser == null)
            throw new IllegalArgumentException("Comments created by others can not be deleted.");

        answerService.delete(loginUser, questionId, id);
        return String.format("redirect:/questions/%d", questionId);
    }
}
