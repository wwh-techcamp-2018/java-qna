package codesquad.web;

import codesquad.domain.Question;
import codesquad.domain.User;
import codesquad.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    public static final String SESSIONED_USER = "sessionedUser";
    @Autowired
    private QuestionService questionService;

    @PostMapping("")
    public String create(Question question, HttpSession session) {
        User loginUser = (User) session.getAttribute(SESSIONED_USER);
        if (loginUser == null) {
            return "/user/login";
        }
        question.create(loginUser);
        questionService.create(question);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionService.getQuestionById(id));
        return "/qna/show";
    }

    @GetMapping("/{id}/form")
    public String update(@PathVariable Long id,
                         Model model) {
        model.addAttribute("question", questionService.getQuestionById(id));
        return "/qna/updateForm";
    }


    @PutMapping("/{id}")
    public String update(@PathVariable Long id, Question updatedQuestion, HttpSession session) {
        User loginUser = (User) session.getAttribute(SESSIONED_USER);
        questionService.update(loginUser, updatedQuestion, id);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        User loginUser = (User) session.getAttribute(SESSIONED_USER);
        if (loginUser == null) {
            return "/user/login";
        }
        questionService.delete(loginUser, id);
        return "redirect:/";
    }
}