package codesquad.web;

import codesquad.domain.Question;
import codesquad.domain.User;
import codesquad.exception.*;
import codesquad.repository.QuestionRepository;
import codesquad.repository.UserRepository;
import codesquad.utility.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    public String list(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "/index";
    }

    @GetMapping("/form")
    public String question(HttpSession httpSession) {
        Authentication.needLogin(httpSession);
        return "/qna/form";
    }

    @PostMapping
    public String create(Question question, HttpSession httpSession) {
        Long id = Authentication.getId(httpSession).orElseThrow(UserSessionNotFoundException::new);
        User writer = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        question.setWriter(writer);
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model, HttpSession httpSession) {
        Question question = getQuestion(id);
        Long sessionId = Authentication.getId(httpSession).orElse(Authentication.INVALID_USER_ID);
        model.addAttribute("question", question);
        model.addAttribute("matchWriter", question.matchWriter(sessionId));
        return "/qna/show";
    }

    private Question getQuestion(Long id) {
        return questionRepository.findById(id).orElseThrow(QuestionNotFoundException::new);
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession httpSession) {
        Long sessionId = Authentication.getId(httpSession)
                .orElseThrow(UserSessionNotFoundException::new);
        Question question = getQuestion(id);
        if (!question.matchWriter(sessionId))
            throw new UserNotMatchException();
        model.addAttribute("question", question);
        return "/qna/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable long id, Question question, HttpSession httpSession) {
        Question questionFromDb = questionRepository.findById(id).get();
        Long sessionId = Authentication.getId(httpSession).orElseThrow(UserSessionNotFoundException::new);
        User writer = userRepository.findById(sessionId).orElseThrow(UserNotFoundException::new);
        question.setWriter(writer);
        questionFromDb.update(question);
        questionRepository.save(questionFromDb);
        return "redirect:/questions/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id, HttpSession httpSession) {
        Long sessionId = Authentication.getId(httpSession).orElseThrow(UserSessionNotFoundException::new);
        Question question = questionRepository.findById(id).orElseThrow(QuestionNotFoundException::new);
        question.delete(sessionId);
        questionRepository.save(question);
        return "redirect:/";
    }
}
