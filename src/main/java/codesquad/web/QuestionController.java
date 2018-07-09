package codesquad.web;

import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;



@Controller
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    QuestionRepository questionRepository;

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "/index";
    }

    @PostMapping("")
    public String create(Question question) {
//        question.setIndex(questions.size()+1);
//        questions.add(question);
        questionRepository.save(question);
        return "redirect:/questions";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("questions", findQuestionWithId(id,this.questionRepository));
        return "/qna/show";
    }

    @GetMapping("/{id}/update")
    public String findQuestion(@PathVariable Long id, Model model) {
        model.addAttribute("question", findQuestionWithId(id,this.questionRepository));
        return "/qna/updateForm";
    }

    @PutMapping("")
    public String update(Question question, HttpServletResponse response) {
        if(question.update(questionRepository)) {
            return "redirect:/questions/" + question.getId();
        }
        WebUtil.alert("비밀번호를 확인해 주세요.", response);
        return "";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        questionRepository.deleteById(id);
        return "redirect:/";
    }

    static Question findQuestionWithId(Long id,QuestionRepository questionRepository){

        Optional<Question> questionOptional = questionRepository.findById(id);
        questionOptional.orElseThrow(() -> new IllegalArgumentException("No user found with id " + id));
        return questionOptional.get();
    }
}
