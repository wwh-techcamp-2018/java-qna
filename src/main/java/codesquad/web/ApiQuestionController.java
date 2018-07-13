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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController // for transfer json data : api 반환 타입을 json 으로 반환하겠다.
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiQuestionController {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping
    public Answer create(@PathVariable Long questionId, @RequestBody Answer answer, HttpSession httpSession) {
        Long writerId = Authentication.getId(httpSession)
                .orElseThrow(UserSessionNotFoundException::new);
        User writer = userRepository.findById(writerId)
                .orElseThrow(UserNotFoundException::new);
        Question question = questionRepository.findById(questionId)
                .orElseThrow(QuestionNotFoundException::new);

        answer.setWriter(writer);
        answer.setQuestion(question);
        Answer answer_temp = answerRepository.save(answer);
        return answer_temp;
    }

    @DeleteMapping("/{answerId}")
    public Answer delete(@PathVariable Long questionId, @PathVariable Long answerId, HttpSession httpSession) {
        Answer answer = answerRepository.findById(answerId).get();
        Long userId = Authentication.getId(httpSession).orElseThrow(UserSessionNotFoundException::new);
        answer.delete(userId);
        return answerRepository.save(answer);

    }
}
