package codesquad.web.question;

import codesquad.domain.question.Answer;
import codesquad.domain.question.Question;
import codesquad.domain.api.Result;
import codesquad.domain.user.User;
import codesquad.dto.question.AnswerDto;
import codesquad.exception.api.NotFoundApiException;
import codesquad.exception.api.UnauthorizedApiException;
import codesquad.repository.question.AnswerRepository;
import codesquad.repository.question.QuestionRepository;
import codesquad.util.AuthenticationUtil;
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

    @PostMapping("")
    public Answer create(@PathVariable Long questionId, @RequestBody AnswerDto answerDto, HttpSession session) {
        User loginUser = AuthenticationUtil.getMaybeUserFromSession(session).orElseThrow(UnauthorizedApiException::new);
        Question question = questionRepository.findById(questionId)
                .orElseThrow(NotFoundApiException::new);
        Answer answer = new Answer(question, loginUser, answerDto.getContents());
        return answerRepository.save(answer);
    }

    @DeleteMapping("/{answerId}")
    public Result delete(@PathVariable Long questionId, @PathVariable Long answerId, HttpSession session) {
        User loginUser = AuthenticationUtil.getMaybeUserFromSession(session).orElseThrow(UnauthorizedApiException::new);
        answerRepository.delete(answerRepository.findById(answerId)
                .filter(correctAnswer -> correctAnswer.matchWriter(loginUser))
                .orElseThrow(UnauthorizedApiException::new));
        return Result.ok();
    }
}