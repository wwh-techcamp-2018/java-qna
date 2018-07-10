package codesquad.web;

import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import codesquad.exception.UnAuthorizedUserException;
import codesquad.exception.UnidentifiedUserException;
import codesquad.util.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;
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
        model.addAttribute("question", getQuestionByIndex(index));
        return "/qna/show";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, Question modifiedQuestion, HttpSession session) throws Exception {
        utils.checkLogin(session);
        utils.checkSameUser(session, getQuestionByIndex(id).getWriterId());
        questionRepository.save(getQuestionByIndex(id).update(modifiedQuestion));
        return "redirect:/questions/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session) throws Exception {
        System.out.println("\ndelete\n");
        Question question = getQuestionByIndex(id);
        utils.checkLogin(session);
        utils.checkSameUser(session, getQuestionByIndex(id).getWriterId());
        questionRepository.delete(question);

        return "redirect:/";
    }

    @GetMapping("/{id}/updateform")
    public String updateForm(@PathVariable Long id, HttpSession session, Model model) throws Exception {
        utils.checkLogin(session);
        utils.checkSameUser(session, getQuestionByIndex(id).getWriterId());
        model.addAttribute("question", getQuestionByIndex(id));
        return "/qna/updateForm";
    }

    private Question getQuestionByIndex(Long index) {
        return questionRepository.findById(index).orElseThrow(NullPointerException::new);
    }

}
