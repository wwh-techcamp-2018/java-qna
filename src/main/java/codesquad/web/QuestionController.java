package codesquad.web;

import codesquad.domain.Question;
import codesquad.domain.User;
import codesquad.service.QuestionService;
import codesquad.util.SessionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/form")
    public String form(HttpSession session) {
        User loginUser = SessionUtils.getSessionedUser(session);
        if(loginUser == null) {
            return "/user/login";
        }
        return "/qna/form";
    }

    @PostMapping("")
    public String create(Question question, HttpSession session) {
        User loginUser = SessionUtils.getSessionedUser(session);
        questionService.create(question, loginUser);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionService.getQuestionById(id));
        log.info("question : {}", questionService.getQuestionById(id));
        return "/qna/show";
    }

    @GetMapping("/{id}/form")
    public String update(@PathVariable Long id,
                         Model model, HttpSession session) {
        User loginUser = SessionUtils.getSessionedUser(session);
        Question savedQuestion = questionService.getQuestionById(id);

        if(!savedQuestion.isMatchByUserId(loginUser)) {
            throw new IllegalArgumentException("Other people's posts can not be edited.");
        }

        model.addAttribute("question", questionService.getQuestionById(id));
        return "/qna/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, Question updatedQuestion, HttpSession session) {
        User loginUser = SessionUtils.getSessionedUser(session);
        questionService.update(loginUser, updatedQuestion, id);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        User loginUser = SessionUtils.getSessionedUser(session);
        questionService.delete(loginUser, id);
        return "redirect:/";
    }
}