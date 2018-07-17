package codesquad.web;

import codesquad.domain.Answer;
import codesquad.domain.Question;
import codesquad.domain.Result;
import codesquad.domain.User;
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
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("")
    public Answer create(@PathVariable Long questionId, @RequestBody Answer inputAnswer, HttpSession session) {
        User loginUser = SessionUtil.getUser(session);
        Question question = QuestionController.findById(questionId,questionRepository);
        Answer answer = new Answer(loginUser, question, inputAnswer.getContents());
        question.addAnswer(answer);
        return  answerRepository.save(answer);
    }


    @DeleteMapping("/{answerId}")
    public Result delete(@PathVariable Long questionId, @PathVariable Long answerId, HttpSession session){
        User user = SessionUtil.getUser(session);
        Question question = QuestionController.findById(questionId,questionRepository);
        question.removeAnswer(answerId, user);
        questionRepository.save(question);
        return new Result().ok(answerId);
    }

}