package codesquad.web;

import codesquad.domain.Answer;
import codesquad.domain.AnswerDto;
import codesquad.domain.Question;
import codesquad.domain.User;
import codesquad.exception.NullQuestionException;
import codesquad.repository.AnswerRepository;
import codesquad.repository.QuestionRepository;
import codesquad.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {
    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

    @PostMapping("")
    public Answer create(@PathVariable Long questionId, @RequestBody AnswerDto answerDto, HttpSession session) {
        User loginUser = SessionUtil.getUser(session);
        Question question = questionRepository.findById(questionId).orElseThrow(NullQuestionException::new);
        Answer answer = new Answer(loginUser, answerDto, question);
        question.addAnswer(answer);

        return answerRepository.save(answer);
    }

    @DeleteMapping("/{answerId}")
    public Answer delete(@PathVariable Long questionId, @PathVariable Long answerId, HttpSession session) {
        User loginUser = SessionUtil.getUser(session);
        Answer answer = answerRepository.findById(answerId).get();
        answer.delete(loginUser);

        return answerRepository.save(answer);
    }
}
