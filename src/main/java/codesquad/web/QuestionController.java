package codesquad.web;

import codesquad.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;


@Controller
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("")
    public String create(Question question, HttpSession session) {
        question.setWriter(SessionUtil.getUser(session));
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

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, Question question, HttpSession session, Model model) throws UserNotMatchException {
        User user = SessionUtil.getUser(session);
        question.setWriter(user);
        Question questionOrigin = findById(id);
        questionOrigin.update(question);
        questionRepository.save(questionOrigin);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session, Model model) throws UserNotMatchException {
        User user = SessionUtil.getUser(session);
        Question questionOrigin = findById(id);
        questionOrigin.isWriter(user);
        questionRepository.delete(questionOrigin);
        return "redirect:/";
    }

    @GetMapping("/form")
    public String showForm(HttpSession session) {
        SessionUtil.getUser(session);
        return "qna/form";
    }

    public Question findById(Long id) {
        Optional<Question> questionOptional = questionRepository.findById(id);
        questionOptional.orElseThrow(() -> new IllegalArgumentException());
        return questionOptional.get();
    }
}
