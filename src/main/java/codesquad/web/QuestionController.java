package codesquad.web;

import codesquad.domain.Answer;
import codesquad.domain.AnswerRepository;
import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import codesquad.exception.UnAuthorizedUserException;
import codesquad.exception.UnDeletableQuestionException;
import codesquad.exception.UnidentifiedUserException;
import codesquad.util.SessionUtils;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.Null;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;
    private SessionUtils utils = SessionUtils.getInstance();

    @GetMapping("")
    public String createForm(HttpSession session) throws Exception {
        utils.checkLogin(session);
        return "/qna/form";
    }

    @PostMapping("")
    public String create(Question question, HttpSession session) {
        question.setTime();
        question.setWriter(utils.getUserFromSession(session));
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/{index}")
    public String show(@PathVariable Long index, Model model) {
        Question question = getQuestionByIndex(index);
        List<Answer> answers = answerRepository.findByQuestionId(index)
                .stream()
                .filter(answer -> !answer.isDeleted())
                .collect(Collectors.toList());

        model.addAttribute("question", question);
        model.addAttribute("answers", answers);
        model.addAttribute("answerNum", answers.size());
        return "/qna/show";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, Question modifiedQuestion, HttpSession session) {
        checkLoginAndSameUser(id, session);
        questionRepository.save(getQuestionByIndex(id).update(modifiedQuestion));
        return "redirect:/questions/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        utils.checkLogin(session);
        Question question = getQuestionByIndex(id);

        question.delete(utils.getUserFromSession(session));
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/{id}/updateform")
    public String updateForm(@PathVariable Long id, HttpSession session, Model model) {
        checkLoginAndSameUser(id, session);
        model.addAttribute("question", getQuestionByIndex(id));
        return "/qna/updateForm";
    }

    private Question getQuestionByIndex(Long index) {
        return questionRepository.findById(index).orElseThrow(NullPointerException::new);
    }

    private void checkLoginAndSameUser(Long questionId, HttpSession session) {
        utils.checkLogin(session);
        utils.checkSameUser(session, getQuestionByIndex(questionId).getWriterId());
    }
}
