package codesquad.web;

import codesquad.domain.Answer;
import codesquad.domain.Question;
import codesquad.domain.Result;
import codesquad.domain.User;
import codesquad.exception.InvalidLoginException;
import codesquad.repository.AnswerRepository;
import codesquad.repository.QuestionRepository;
import codesquad.util.SessionUtil;
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

    @PostMapping
    public Answer create(@PathVariable Long questionId, @RequestBody Answer answer, HttpSession session) {
        SessionUtil.getUser(session).orElseThrow(() -> new InvalidLoginException("로그인이 필요합니다"));

        User loginUser = SessionUtil.getUser(session).get();
        Question question = questionRepository.findById(questionId).get();
        System.out.println(question.getContents());
        System.out.println(question.getTitle());
        System.out.println(question.getWriter());
        Answer newAnswer = new Answer(answer.getContents(), question, loginUser);
        return answerRepository.save(newAnswer);
    }

    @DeleteMapping("/{answerId}")
    public Result delete(@PathVariable Long answerId, HttpSession session) {
        Result result = new Result(answerId);
        Answer answer = answerRepository.findById(answerId).get();
        if(!answer.matchWriter(SessionUtil.getUser(session).get())) {
            return result.fail("다른 사람의 댓글을 삭제할 수 없습니다.");
        }
        answerRepository.save(answer.delete());
        return result.ok();
    }

}
