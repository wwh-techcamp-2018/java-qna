package codesquad.web;

import codesquad.domain.Question;
import codesquad.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {

    private List<Question> questions = new ArrayList<>();

    // post 방식으로 받을 것이며, /users로 들어오게 되면 이 메서드를 실행하라는 뜻!
    @PostMapping("/questions")
    public String create(Question question) {
        questions.add(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String create(Model model) {
        model.addAttribute("questions", questions);
        return "/index";
    }

    @GetMapping("/questions/{index}")
    public String show(@PathVariable int index,Model model) {
        model.addAttribute("question", questions.get(index));
        return "/qna/show";
    }
    @GetMapping("/page/qna")
    public String pageQna(){
        return "/qna/form";
    }

}
