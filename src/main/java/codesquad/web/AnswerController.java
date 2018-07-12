package codesquad.web;

import codesquad.domain.*;
import codesquad.util.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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

    private SessionUtils utils = SessionUtils.getInstance();

    @Transactional
    @PostMapping("")
    public String create(@PathVariable Long questionId, HttpSession session, Answer target) {
        User user = utils.getUserFromSession(session);
        Question question = questionRepository.findById(questionId).orElseThrow(NullPointerException::new);
        Answer answer = new Answer(user, target.getContents());
        answer.update(user, question);
        question.addAnswers(answer);
        answerRepository.save(answer);

        return "redirect:/questions/" + questionId;
    }

    @Transactional
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session, Answer target) {
        // answer의 deleted 를 true로
        Answer answer = answerRepository.findById(target.getId()).orElseThrow(NullPointerException::new);
        utils.checkLogin(session);
        answer.delete(utils.getUserFromSession(session));
        return "redirect:/questions/" + questionId;
    }
}
