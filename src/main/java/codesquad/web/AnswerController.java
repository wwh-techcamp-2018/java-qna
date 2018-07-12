package codesquad.web;

import codesquad.domain.Answer;
import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import codesquad.domain.User;
import codesquad.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
    @Autowired
    QuestionRepository questionRepository;

    @GetMapping("")
    public String createForm(HttpSession session, @PathVariable Long questionId, Model model) {
        SessionUtil.validateSession(session);
        model.addAttribute("question", QuestionController.findById(questionId, questionRepository));
        return "/qna/answerForm";
    }

    @PostMapping("")
    public String create(HttpSession session, Answer answer, @PathVariable Long questionId) {
        SessionUtil.validateSession(session);
        User user = SessionUtil.getSessionUser(session);
        answer.setWriter(user);
        Question question = QuestionController.findById(questionId, questionRepository);
        question.add(answer);
        questionRepository.save(question);

        return "redirect:/questions/" + question.getId();
    }

    @DeleteMapping("/{answerId}")
    public String delete(HttpSession session, @PathVariable Long questionId, @PathVariable Long answerId) {
        SessionUtil.validateSession(session);
        Question question = QuestionController.findById(questionId, questionRepository);
        question.removeAnswer(answerId, SessionUtil.getSessionUser(session));
        questionRepository.save(question);
        return "redirect:/questions/" + questionId;
    }
}
