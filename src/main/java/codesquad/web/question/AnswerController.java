package codesquad.web.question;

import codesquad.SessionUtil;
import codesquad.domain.question.Answer;
import codesquad.domain.question.AnswerRepository;
import codesquad.domain.question.Question;
import codesquad.domain.question.QuestionRepository;
import codesquad.domain.user.User;
import codesquad.dto.question.AnswerDto;
import codesquad.exception.user.UserNotFoundException;
import codesquad.exception.RedirectableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("")
    public String answer(@PathVariable Long questionId, AnswerDto dto, HttpSession session) {
        User user = SessionUtil.getMaybeUser(session)
                .orElseThrow(() -> new RedirectableException(makeRedirectPath(questionId)));
        Question question = questionRepository.findById(questionId).orElseThrow(UserNotFoundException::new);
        answerRepository.save(dto.toEntity(question, user));
        return makeRedirectPath(questionId);
    }


    @DeleteMapping("/{id}")
    public String deleteAnswer(@PathVariable Long questionId,@PathVariable Long id, HttpSession session) {
        User loginedUser = SessionUtil.getUser(session);
        Answer answer = answerRepository.findById(id).orElseThrow(UserNotFoundException::new);
        answer.delete(loginedUser);
        answerRepository.save(answer);
        return makeRedirectPath(questionId);
    }

    private String makeRedirectPath(Long questionId) {
        return "redirect:/questions/" + questionId;
    }
}
