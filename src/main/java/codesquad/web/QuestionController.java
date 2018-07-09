package codesquad.web;

import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/qnas")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("qnas", questionRepository.findAll());
        return "index";
    }

    @PostMapping("")
    public String create(Question question) {
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("qna", questionRepository.findById(id).get());
        return "/qna/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable("id") Question question, Model model) {
        model.addAttribute("qna", question);
        return "/qna/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, Question question) {
        Question updatedQuestion = questionRepository.findById(id).get().updateQuestion(question);
        questionRepository.save(updatedQuestion);
        return "redirect:/qnas";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        questionRepository.deleteById(id);
        return "redirect:/qnas";
    }

}
