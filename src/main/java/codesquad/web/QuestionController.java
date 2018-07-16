package codesquad.web;

import codesquad.domain.*;
import codesquad.dto.QnAObject;
import codesquad.util.SessionUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;


    @PostMapping("/questions")
    public String create(Question question, HttpSession session) {
        User currentUser = SessionUtility.getCurrentUser(session);
        if (currentUser == null) {
            return "redirect:/users/login";
        }
        question.setWriter(SessionUtility.getCurrentUser(session));
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/questions/form")
    public String getQuestionForm(HttpSession session, Model model) {
        User currentUser = SessionUtility.getCurrentUser(session);
        if (currentUser == null) {
            return "redirect:/users/login";
        }
        model.addAttribute("user", currentUser);
        return "/qna/form";
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("questions", questionRepository.findAllByDeletedFalse());
        return "/qna/index";
    }

    @GetMapping("/questions/{index}")
    public String show(@PathVariable int index, HttpSession session, Model model) {
        Question question = questionRepository.findById(index).orElse(null);
        if (question == null)
            return "redirect:/";

        User questionUser = question.getWriter();
        List<Answer> answers = answerRepository.findAllByDeletedFalseAndQuestionId(index);
        QnAObject dtoObject = new QnAObject(questionUser, questionUser.equalsUser(SessionUtility.getCurrentUser(session)),
                question, answers, answers.size());
        model.addAttribute("dtoObject", dtoObject);
        return "/qna/show";

    }

    @DeleteMapping("/questions/{index}")
    public String delete(@PathVariable int index, HttpSession session, Model model) {
        Optional<Question> maybeQuestion = questionRepository.findById(index);
        if (!maybeQuestion.isPresent()) {
            return "redirect:/";
        }

        User currentUser = SessionUtility.getCurrentUser(session);

        Question question = maybeQuestion.get();
        if(!question.isDeletable(currentUser, answerRepository.findAllByDeletedFalseAndQuestionId(index)))
            return "redirect:/questions/error";
        question.setDeleted();
        questionRepository.save(question);
        return "redirect:/";
    }


    @GetMapping("/questions/error")
    public String getQuestionError() {
        return "qna/error";
    }

    @GetMapping("/questions/updateForm/{index}")
    public String getQuestionInfo(@PathVariable int index, Model model) {
        Optional<Question> maybeQuestion = questionRepository.findById(index);
        if (!maybeQuestion.isPresent()) {
            return "redirect:/";
        }
        model.addAttribute("question", maybeQuestion.get());
        return "qna/updateForm";
    }

    @PutMapping("questions/{index}")
    public String updateQuestionInfo(@PathVariable int index, Question updateQuestion, HttpSession session) {
        Optional<Question> maybeQuestion = questionRepository.findById(index);
        if (!maybeQuestion.isPresent()) {
            return "redirect:/";
        }
        Question question = maybeQuestion.get();
        User currentUser = SessionUtility.getCurrentUser(session);
        try {
            question.updateQuestion(updateQuestion, currentUser);
        } catch (IllegalArgumentException e) {
            return "redirect:/questions/error";
        }
        questionRepository.save(question);
        return "redirect:/questions/" + index;
    }

}
