package codesquad.web;

import codesquad.domain.Answer;
import codesquad.service.QuestionService;
import codesquad.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("")
    public String create(@PathVariable long questionId, Answer answer, HttpSession session) {
        questionService.addAnswer(SessionUtil.getSessionUser(session), questionId, answer);
        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("/{answerId}")
    public String delete(@PathVariable long questionId, @PathVariable long answerId, HttpSession session) {
        questionService.deleteAnswer(SessionUtil.getSessionUser(session), questionId, answerId);
        return "redirect:/questions/" + questionId;
    }


}
