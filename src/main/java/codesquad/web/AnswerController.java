package codesquad.web;

import codesquad.domain.*;
import codesquad.exception.NullAnswerException;
import codesquad.exception.NullQuestionException;
import codesquad.exception.UserNotMatchException;
import codesquad.repository.QuestionRepository;
import codesquad.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("")
    public String create(@PathVariable Long questionId, Answer answer, HttpSession session) {
        answer.setWriter(SessionUtil.getUser(session));
        Question question = QuestionController.findById(questionId,questionRepository);
        question.addAnswer(answer);
        questionRepository.save(question);
        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        User user = SessionUtil.getUser(session);
        Question question = QuestionController.findById(questionId,questionRepository);
        question.removeAnswer(id, user);
        questionRepository.save(question);
        return "redirect:/questions/" + questionId;
    }
}
