package codesquad.web;

import codesquad.Exception.RedirectException;
import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import codesquad.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;


@Controller
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    QuestionRepository questionRepository;

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("questions", questionRepository.findAllPresent());
        return "/index";
    }

    @GetMapping("/new")
    public String createForm(HttpSession session) {
        SessionUtil.validateSession(session);
        return "redirect:/qna/form";
    }

    @PostMapping("")
    public String create(Question question, HttpSession session) {
        SessionUtil.validateSession(session);
        question.setWriter(SessionUtil.getSessionUser(session));
        questionRepository.save(question);
        return "redirect:/questions";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("question", findById(id, this.questionRepository));
        return "/qna/show";
    }

    @GetMapping("/{id}/update")
    public String findQuestion(@PathVariable Long id, HttpSession session, Model model) {
        SessionUtil.validateSession(session);
        Question question = findById(id, this.questionRepository);
        question.validateWriter(SessionUtil.getSessionUser(session));
        model.addAttribute("question", question);
        return "/qna/updateForm";
    }

    @PutMapping("")
    public String update(Question question, Model model, HttpSession session) {
        Question original = findById(question.getId(), questionRepository);
        original.update(question);
        questionRepository.save(original);
        return "redirect:/questions/" + question.getId();
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session, Model model) {
        SessionUtil.validateSession(session);
        Question question = findById(id, questionRepository);
        question.validateWriter(SessionUtil.getSessionUser(session));
        question.delete();
        questionRepository.save(question);
        return "redirect:/";
    }

    public static Question findById(Long id, QuestionRepository questionRepository) {
        Optional<Question> questionOptional = questionRepository.findById(id);
        questionOptional.orElseThrow(() -> new IllegalArgumentException("No question found with id " + id));
        return questionOptional.get();
    }
}
