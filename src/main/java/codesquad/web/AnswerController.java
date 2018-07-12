package codesquad.web;

import codesquad.web.domain.Answer;
import codesquad.web.domain.AnswerRepository;
import codesquad.web.domain.QuestionRepository;
import codesquad.web.domain.User;
import codesquad.web.exception.NoLoginException;
import codesquad.web.util.RepositoryUtil;
import codesquad.web.util.SessionUtil;
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

    @PostMapping
    public String create(@PathVariable Long questionId, String contents, HttpSession session) {
        System.out.print("create");
        User loginUser = SessionUtil.getUser(session);
        Answer answer = new Answer(RepositoryUtil.findQuestionById(questionId, questionRepository),
                contents,
                loginUser);
        answerRepository.save(answer);

        return "redirect:/questions/{questionId}";
    }

    @DeleteMapping("/{answerId}")
    public String delete(@PathVariable Long questionId, @PathVariable Long answerId, HttpSession session) {
        if (!RepositoryUtil.setDeleted(answerRepository, answerId, session)) {
            throw new IllegalArgumentException("다른 작성자의 댓글을 지울 수 없습니다.");
        }
        return "redirect:/questions/{questionId}";
    }
}
