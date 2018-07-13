package codesquad.web.question;

import codesquad.SessionUtil;
import codesquad.domain.question.Answer;
import codesquad.domain.question.AnswerRepository;
import codesquad.domain.question.Question;
import codesquad.domain.question.QuestionRepository;
import codesquad.domain.user.User;
import codesquad.dto.question.AnswerDto;
import codesquad.exception.user.PermissionDeniedException;
import codesquad.exception.user.UserNotFoundException;
import codesquad.exception.RedirectableException;
import netscape.security.ForbiddenTargetException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api/questions/{questionId}/answers")
@RestController
public class AnswerController {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping(value = "")
    @Transactional
    public Answer answer(@PathVariable Long questionId, @RequestBody AnswerDto dto, HttpSession session) {
        User user = SessionUtil.getMaybeUser(session)
                .orElseThrow(() -> new RedirectableException("redirect:/questions/" + questionId));
        Question question = questionRepository.findById(questionId).orElseThrow(UserNotFoundException::new);
        return answerRepository.save(dto.toEntity(question, user));
    }


    @DeleteMapping("/{id}")
    @Transactional
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Long> deleteAnswer(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        Map<String, Long> response = new HashMap<>();
        User loginedUser = SessionUtil.getUser(session);
        Answer answer = answerRepository.findById(id).orElseThrow(UserNotFoundException::new);
        answer.delete(loginedUser);
        response.put("answerId", id);
        return response;
    }

    @ExceptionHandler(PermissionDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void permissionDeniedExceptionHandle() {

    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void userNotFoundExceptionHandle() {

    }
}
