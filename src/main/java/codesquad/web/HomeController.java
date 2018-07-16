package codesquad.web;

import codesquad.domain.Question;
import codesquad.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    QuestionRepository questionRepository;

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("questions", getQuestionList());
        return "/qna/list";
    }

    public Iterable<Question> getQuestionList() {
        return questionRepository.findAll();
    }
}
