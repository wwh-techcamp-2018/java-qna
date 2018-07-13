package codesquad.web;

import codesquad.domain.*;
import codesquad.repository.AnswerRepository;
import codesquad.service.QuestionService;
import codesquad.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Persistent;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import static codesquad.web.UserController.SESSION_KEY;


@Controller
@RequestMapping("/questions")
@Transactional
public class QnaController {

    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    @GetMapping("/form")
    public String registForm(HttpSession session){
//        Object sessionedUserObject = session.getAttribute(SESSION_KEY);
//        if(sessionedUserObject == null){
//            return "redirect:/users/login/form";
//        }
        SessionUtil.validateLogin(session);
        return "/qna/form";
    }
    @PostMapping
    public String create(String title, String contents, HttpSession session) {

        User writeUser = SessionUtil.getSessionedUser(session);
        questionService.addQuestion(title, contents, writeUser);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable long id, Model model) {
        model.addAttribute("question", questionService.getQuestionById(id));
//        model.addAttribute("numAnswer", numAnswer);
        return "/qna/show";
    }

    @GetMapping("/{id}/form")
    public String viewForUpdate(@PathVariable long id, Model model, HttpSession session) {
        User sessionedUser = SessionUtil.getSessionedUser(session);
        model.addAttribute("question", questionService.getModifiableQuestion(sessionedUser, id));
        return "/qna/updateForm";
    }

    @PutMapping("/{id}")
    public String updateQuestion(@PathVariable long id, String contents, String title, HttpSession session) {
        User sessionedUser = SessionUtil.getSessionedUser(session);
        questionService.updateQuestion(id, new Question(sessionedUser, title, contents));
        return "redirect:/questions/{id}";
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable long id,HttpSession session) {
        User sessionedUser = SessionUtil.getSessionedUser(session);
        questionService.deleteQuestionById(sessionedUser, id);
        return "redirect:/";
    }
    @Transactional
    @PostMapping("/{questionId}/answers")
    public String createAnswer(@PathVariable long questionId, String contents, HttpSession session) {
        Question question = questionService.getQuestionById(questionId);
        Answer answer = new Answer(SessionUtil.getSessionedUser(session), contents);
        question.addAnswer(answer);
        questionService.updateQuestion(questionId, question);
        return "redirect:/questions/{questionId}";
    }
    @Transactional
    @DeleteMapping("/{questionId}/answers/{answerId}")
    public String deleteAnswer(@PathVariable long questionId, @PathVariable long answerId, HttpSession session) {
        User sessionedUser = SessionUtil.getSessionedUser(session);
        questionService.deleteAnswer(answerId, sessionedUser);
        return "redirect:/questions/{questionId}";
    }
}
