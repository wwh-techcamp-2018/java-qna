package codesquad.web;

import codesquad.domain.*;
import codesquad.domain.result.AnswerResult;
import codesquad.util.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class ApiAnswerController {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    private SessionUtils utils = SessionUtils.getInstance();

    @PostMapping("/questions/{questionId}/answers")
    public AnswerResult create(@PathVariable Long questionId, @RequestBody Answer target, HttpSession session) {
        utils.checkLoginFromApi(session);
        User user = utils.getUserFromSession(session);
        Question question = questionRepository.findById(questionId).orElseThrow(NullPointerException::new);
        Answer answer = new Answer(user, target.getContents());
        System.out.println("\n\n\n\n\n\n\n" + target.toString());
        answer.update(user, question);
        question.addAnswers(answer);
        answerRepository.save(answer);
        return (new AnswerResult()).ok(answer);
    }

    @Transactional
    @DeleteMapping("/questions/{questionId}/answers/{id}")
    public AnswerResult delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        // answer의 deleted 를 true로
        utils.checkLoginFromApi(session);
        Answer answer = answerRepository.findById(id).orElseThrow(NullPointerException::new);
        answer.delete(utils.getUserFromSession(session));
        answerRepository.save(answer);
        return new AnswerResult().ok(answer);
    }
}
