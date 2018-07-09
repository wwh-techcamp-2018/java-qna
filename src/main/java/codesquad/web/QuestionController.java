package codesquad.web;

import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
@RequestMapping({ "/", "/questions" })
public class QuestionController {
    private final String QUESTION_ROOT_PATH = "/questions";
    private long questionId = 0;

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("questions",questionRepository.findAll());
        return "/index";
    }

    @PostMapping("")
    public String create(Question question) {
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("question", findById(id));
        return "/qna/show";
    }

    @GetMapping("/{id}/form")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        model.addAttribute("question", findById(id));
        return "/qna/updateForm";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, Question question) {
        Question questionOrigin = findById(id);
        questionOrigin.update(question);
        questionRepository.save(questionOrigin);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        Question questionOrigin = findById(id);
        questionRepository.delete(questionOrigin);
        return "redirect:/";
    }

    public Question findById(Long id) {
        Optional<Question> questionOptional = questionRepository.findById(id);
        questionOptional.orElseThrow(() -> new IllegalArgumentException());
        return questionOptional.get();
    }
}
