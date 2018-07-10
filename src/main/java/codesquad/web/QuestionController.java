package codesquad.web;

import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import codesquad.domain.User;
import codesquad.exception.QuestionModifyFailException;
import codesquad.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;


    @GetMapping("/form")
    public String questionForm(HttpSession session) {
        User user = UserController.getSessionUser(session);
        if (user == null)
            return "redirect:/users/login";
        return "/qna/form";
    }

    @PostMapping("")
    public String create(Question question, HttpSession session) {
        User user = UserController.getSessionUser(session);
        question.setWriter(user);
        questionService.save(question);
        return "redirect:/";
    }

    @GetMapping("")
    public String list() {
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionService.findById(id));
        return "/qna/show";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        try {
            User user = UserController.getSessionUser(session);
            questionService.deleteById(id, user);
            return "redirect:/";
        } catch (QuestionModifyFailException e) {
            return "redirect:/users/login";
        }
    }

    @GetMapping("/{id}/update")
    public String updateForm(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionService.findById(id));
        return "/qna/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, Question question, HttpSession session) {
        try {
            User user = UserController.getSessionUser(session);
            questionService.updateById(id, question, user);
            return "redirect:/questions/" + id;
        } catch (QuestionModifyFailException e) {
            return "/qna/updateForm_failed";
        }
    }




}
