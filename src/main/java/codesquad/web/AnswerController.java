package codesquad.web;

import codesquad.domain.Answer;
import codesquad.domain.Question;
import codesquad.domain.User;
import codesquad.exception.QuestionNotFoundException;
import codesquad.exception.UserNotFoundException;
import codesquad.exception.UserSessionNotFoundException;
import codesquad.repository.AnswerRepository;
import codesquad.repository.QuestionRepository;
import codesquad.repository.UserRepository;
import codesquad.utility.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public String create(@PathVariable Long questionId, HttpSession httpSession, Answer answer) {
        Long writerId = Authentication.getId(httpSession).orElseThrow(UserSessionNotFoundException::new);
        User writer = userRepository.findById(writerId).orElseThrow(UserNotFoundException::new);
        Question question = questionRepository.findById(questionId).orElseThrow(QuestionNotFoundException::new);
        answer.setWriter(writer);
        answer.setQuestion(question);
        answerRepository.save(answer);
        return String.format("redirect:/questions/%d", questionId);
    }

    @DeleteMapping("/{answerId}")
    public String delete(@PathVariable Long questionId, @PathVariable Long answerId, HttpSession httpSession) {
        Answer answer = answerRepository.findById(answerId).get();
        Long userId = Authentication.getId(httpSession).orElseThrow(UserSessionNotFoundException::new);
        answer.delete(userId);
        answerRepository.save(answer);
        return String.format("redirect:/questions/%d", questionId);
    }
}
