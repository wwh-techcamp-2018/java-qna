package codesquad.web;

import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import codesquad.domain.User;
import codesquad.domain.UserRepository;
import codesquad.util.SessionHandler;
import org.omg.CosNaming.NamingContextPackage.NotFound;
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

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public String create(Question question,HttpSession session) {
        User user = userRepository.findById(SessionHandler.getId(session)).get();
        question.setWriter(user);
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionRepository.findById(id).get());
        return "/qna/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        User user = userRepository.findById(SessionHandler.getId(session)).get();
        Question question = questionRepository.findById(id).get();
        if(question.checkWriter(user)){
            question.setWriter(user);
            model.addAttribute("question", question);
            return "/qna/updateForm";
        }
        return "/qna/update_failed";

    }

    @GetMapping("/form")
    public String createPost() {
        return "/qna/form";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, Question newQuestion) {
        Question question = questionRepository.findById(id).get();
        question.update(newQuestion);
        questionRepository.save(question);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id,HttpSession session) {
        User user = userRepository.findById(SessionHandler.getId(session)).get();
        Question question = questionRepository.findById(id).get();
        if(question.checkWriter(user)){
            questionRepository.deleteById(id);
            return "redirect:/";

        }
        return "/qna/update_failed";

    }

}
