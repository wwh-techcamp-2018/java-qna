package codesquad.web.api;

import codesquad.web.domain.*;
import codesquad.web.util.RepositoryUtil;
import codesquad.web.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {

    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping
    public Answer create(@PathVariable Long questionId, @RequestBody AnswerDto answerDto, HttpSession session) {
        User loginUser = SessionUtil.getUser(session);
        Answer answer = new Answer(RepositoryUtil.findQuestionById(questionId, questionRepository),
                answerDto.getContents(),
                loginUser);

        return answerRepository.save(answer);
    }

    @DeleteMapping("/{answerId}")
    public Result delete(@PathVariable Long questionId, @PathVariable Long answerId, HttpSession session) {
        if (!RepositoryUtil.setDeleted(answerRepository, answerId, session)) {
            throw new IllegalArgumentException("다른 작성자의 댓글을 지울 수 없습니다.");
        }
        return Result.ok(answerId.toString());
    }
}
