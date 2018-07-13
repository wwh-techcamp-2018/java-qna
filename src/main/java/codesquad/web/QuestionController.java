package codesquad.web;

import codesquad.domain.Answer;
import codesquad.domain.Question;
import codesquad.exception.QuestionNotFoundException;
import codesquad.exception.UserNotFoundException;
import codesquad.exception.UserNotInSessionException;
import codesquad.repository.AnswerRepository;
import codesquad.repository.QuestionRepository;
import codesquad.domain.User;
import codesquad.repository.UserRepository;
import codesquad.util.SessionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("questions", questionRepository.findAllByIsDeletedFalse());
        return "/index";
    }

    @PostMapping
    public String create(Question question, HttpSession session) {
        User user = getUser(session);
        question.setWriter(user);
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/form")
    public String createPost() {
        return "/qna/form";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        List<Answer> answers = answerRepository.findAllByQuestionIdAndIsDeletedFalse(id);
        model.addAttribute("question", questionRepository.findByIdAndIsDeletedFalse(id).orElseThrow(QuestionNotFoundException::new));
        model.addAttribute("answers", answers);
        model.addAttribute("num", answers.size());

        return "/qna/show";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, Question newQuestion, HttpSession session) {
        Question question = questionRepository.findByIdAndIsDeletedFalse(id).orElseThrow(QuestionNotFoundException::new);
        question.updateByUser(getUser(session), newQuestion);
        questionRepository.save(question);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        User user = getUser(session);
        Question question = questionRepository.findByIdAndIsDeletedFalse(id).orElseThrow(QuestionNotFoundException::new);
        List<Answer> answers = answerRepository.findAllByQuestionIdAndIsDeletedFalse(id);
        deleteQuestion(user, question, answers);
        deleteAnswerByQuestionId(user, id);
        return "redirect:/";
    }

    private void deleteAnswerByQuestionId(User user, Long questionId) {
        List<Answer> answers = answerRepository.findAllByQuestionIdAndIsDeletedFalse(questionId)
                .stream()
                .map(answer -> answer.deleteByUser(user))
                .collect(Collectors.toList());
        answerRepository.saveAll(answers);
    }

    private void deleteQuestion(User user, Question question, List<Answer> answers) {
        question.deleteByUser(user, answers);
        questionRepository.save(question);
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        User user = getUser(session);
        Question question = questionRepository.findByIdAndIsDeletedFalse(id).orElseThrow(QuestionNotFoundException::new);
        question.validateWriter(user);
        model.addAttribute("question", question);
        return "/qna/updateForm";
    }

    private User getUser(HttpSession session) {
        Long uid = SessionHandler.getId(session);
        return userRepository.findById(uid).orElseThrow(UserNotFoundException::new);
    }

}
