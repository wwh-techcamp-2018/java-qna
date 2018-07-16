package codesquad.web;

import codesquad.domain.Answer;
import codesquad.domain.AnswerRepository;
import codesquad.domain.QuestionRepository;
import codesquad.domain.User;
import codesquad.util.SessionUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class ApiController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @DeleteMapping("/questions/{questionId}/answers/{id}")
    public Answer deleteAnswer(@PathVariable int questionId, @PathVariable int id, HttpSession session){
        User currentUser = SessionUtility.getCurrentUser(session);
        Answer answer = answerRepository.findById(id).get();
        if(!answer.isWriter(currentUser)) {
            return null;
        }
        answer.setDeleted();
        return answerRepository.save(answer);
    }

    @PostMapping("/questions/{id}")
    public Answer addAnswer(@PathVariable int id, HttpSession session, @RequestBody Answer answer) {
        User currentUser = SessionUtility.getCurrentUser(session);
        if(currentUser == null) {
            return null;
        }

        answer.setQuestion(questionRepository.findById(id).get());
        answer.setWriter(currentUser);
        return answerRepository.save(answer);
    }

}
