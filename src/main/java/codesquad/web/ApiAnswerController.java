package codesquad.web;

import codesquad.domain.Answer;
import codesquad.domain.Question;
import codesquad.domain.User;
import codesquad.exception.QuestionNotFoundException;
import codesquad.exception.UserNotFoundException;
import codesquad.repository.AnswerRepository;
import codesquad.repository.QuestionRepository;
import codesquad.repository.UserRepository;
import codesquad.util.SessionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public Answer create(@PathVariable Long questionId, @RequestBody Answer answer, HttpSession session) {
        User user = getUser(session);
        Question question = questionRepository.findByIdAndIsDeletedFalse(questionId).orElseThrow(QuestionNotFoundException::new);
        answer.setWriter(user);
        answer.setQuestion(question);
        return answerRepository.save(answer);
    }

    @DeleteMapping("/{answerId}")
    public Answer delete(@PathVariable("questionId") Long questionId, @PathVariable("answerId") Long answerId, HttpSession session) {
        User user = getUser(session);
        Answer answer = answerRepository.findById(answerId).orElseThrow(IllegalAccessError::new);
        answer.deleteByUser(user);
        return answerRepository.save(answer);
    }

    private User getUser(HttpSession session) {
        Long uid = SessionHandler.getId(session);
        return userRepository.findById(uid).orElseThrow(UserNotFoundException::new);
    }

}
