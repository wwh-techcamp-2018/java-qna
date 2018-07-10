package codesquad.web;

import codesquad.web.domain.Question;
import codesquad.web.domain.QuestionRepository;
import codesquad.web.domain.User;
import codesquad.web.util.RepositoryUtil;
import codesquad.web.util.SessionUtil;
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

    @PostMapping
    public String create(Question question, HttpSession session) {
        question.setWriter(SessionUtil.getUser(session));
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable long id, Model model){
        model.addAttribute("question", RepositoryUtil.findQuestionById(id, questionRepository));
        return "/qna/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable long id, Model model, HttpSession session) {
        Question question = RepositoryUtil.findQuestionById(id, questionRepository);
        User writer = question.getWriter();
        if(!writer.equals(SessionUtil.getUser(session))) {
            throw new IllegalArgumentException("다른 사람의 글은 수정할 수 없어..");
        }
        model.addAttribute("question", question);
        return "/qna/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable long id, Question newQuestion) {
        Question question = RepositoryUtil.findQuestionById(id, questionRepository);
        question.update(newQuestion);
        questionRepository.save(question);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id, Model model, HttpSession session){
        Question question = RepositoryUtil.findQuestionById(id, questionRepository);
        User writer = question.getWriter();
        User loginUser = SessionUtil.getUser(session);
        if(loginUser == null){
            return "users/loginForm";
        }
        if(!writer.equals(loginUser)) {
            throw new IllegalArgumentException("다른 사람의 글은 삭제할 수 없어");
        }
        questionRepository.delete(question);
        return "redirect:/";
    }

    @GetMapping("/form")
    public String form(HttpSession session){
        User loginUser = SessionUtil.getUser(session);
        if (loginUser == null) {
            return "redirect:/users/loginForm";
        }
        return "qna/form";
    }
}
