package codesquad.web;

import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import codesquad.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("")
    public String create(Question question) {
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
    public String delete(@PathVariable Long id) {
        questionService.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/{id}/update")
    public String updateForm(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionService.findById(id));
        return "/qna/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, Question question) {
        questionService.updateById(id, question);
        return "redirect:/questions/" + id;
    }


}
