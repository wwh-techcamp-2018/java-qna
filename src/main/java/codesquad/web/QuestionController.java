package codesquad.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {
    List<Question> questions;

    public QuestionController() {
        this.questions = new ArrayList<>();
        this.questions.add(null);
    }


    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("questions", questions);
        return "index";
    }

    @GetMapping("/questions/{id}")
    public String detail(@PathVariable int id, Model model) {
        model.addAttribute("question", questions.get(id));
        model.addAttribute("qid", id);
        return "qna/show";
    }

    @GetMapping("/questions/write")
    public String form() {
        return "qna/form";
    }

    @PostMapping("/questions")
    public String create(Question q, Model model) {
        questions.add(q);
        return "redirect:/";
    }

}
