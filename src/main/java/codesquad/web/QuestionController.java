package codesquad.web;

import codesquad.domain.Question;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {
    List<Question> questionList = new ArrayList<>();

    @PostMapping("/questions")
    public String question(Question question) {
        questionList.add(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String showList(Model model) {
        model.addAttribute("questions", questionList);
        return "index";
    }

    @GetMapping("/questions/{index}")
    public String show(Model model, @PathVariable int index) {
        model.addAttribute("question", questionList.get(index));
        return "/qna/show";
    }
}
