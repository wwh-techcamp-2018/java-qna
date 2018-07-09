package codesquad.web;

import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class QuestionController {

    @Autowired
    private QuestionRepository qRepository;

    public QuestionController() {
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("questions", qRepository.findAll());
        return "index";
    }

    @GetMapping("/questions/{id}")
    public String detail(@PathVariable long id, Model model) {
        model.addAttribute("question", qRepository.findById(id).get());
        return "qna/show";
    }

    @GetMapping("/questions/write")
    public String writeForm() {
        return "qna/form";
    }

    @GetMapping("/questions/{id}/modify")
    public String modifyForm(@PathVariable long id, Model model) {
        model.addAttribute("question", qRepository.findById(id).get());
        return "qna/modifyForm";
    }

    @PostMapping("/questions")
    public String create(Question q, Model model) {
        qRepository.save(q);
        return "redirect:/";
    }

    @PutMapping("/questions/{id}")
    public String modify(@PathVariable long id, Question q, Model model) {
        qRepository.findById(id).get();
        q.setId(id);
        qRepository.save(q);
        return "redirect:/";
    }

    @DeleteMapping("/questions/{id}")
    public String delete(@PathVariable long id) {
        qRepository.deleteById(id);
        return "redirect:/";
    }

}
