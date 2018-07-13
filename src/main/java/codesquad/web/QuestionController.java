package codesquad.web;

import codesquad.domain.Answer;
import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import codesquad.domain.User;
import codesquad.exception.AuthorizationException;
import codesquad.exception.NotFoundException;
import codesquad.exception.NotLoginException;
import codesquad.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionRepository qRepository;

    public QuestionController() {
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("questions", qRepository.findByDeletedFalse());
        return "qna/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable long id, Model model) {
        Question q = qRepository.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("question", q);
        model.addAttribute("answerSize", q.getAnswers().size());
        return "qna/show";
    }

    @GetMapping("/write")
    public String writeForm(HttpSession session) {
        Session.getUser(session);
        return "qna/form";
    }

    @GetMapping("/{id}/modify")
    public String modifyForm(@PathVariable long id, Model model, HttpSession session) {
        User user = Session.getUser(session);
        Question q = qRepository.findById(id).get();
        q.validateWriter(user);
        model.addAttribute("question", q);
        return "qna/modifyForm";
    }

    @PostMapping("")
    public String create(Question q, Model model, HttpSession session) {
        User user = Session.getUser(session);
        q.setWriter(user);
        qRepository.save(q);
        return "redirect:/questions";
    }

    @PutMapping("/{id}")
    public String modify(@PathVariable long id, Question q, HttpSession session) {
        User user = Session.getUser(session);
        Question maybeQuestion = qRepository.findById(id).filter(u -> u.validateWriter(user)).orElseThrow(NotFoundException::new);
        qRepository.save(maybeQuestion.modify(q));
        return "redirect:/questions";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id, HttpSession session) {
        User user = Session.getUser(session);
        Question q = qRepository.findById(id).filter(u -> u.validateWriter(user)).orElseThrow(NotFoundException::new);
        q.delete(user);
        qRepository.save(q);
        return "redirect:/questions";
    }

    @ExceptionHandler({NotLoginException.class, NotFoundException.class, AuthorizationException.class})
    public String handleNotLogin() {
        return "redirect:/users/login";
    }
}
