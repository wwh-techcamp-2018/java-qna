package codesquad.web;

import codesquad.web.domain.Question;
import codesquad.web.domain.QuestionRepository;
import codesquad.web.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping
    public String create(Question question) {
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable long id, Model model){
        model.addAttribute("question", Util.findQuestionById(id, questionRepository));
        return "/qna/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable long id, Model model) {
        model.addAttribute("question", Util.findQuestionById(id, questionRepository));
        return "/qna/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable long id, Question newQuestion) {
        Question question = Util.findQuestionById(id, questionRepository);
        question.update(newQuestion);
        questionRepository.save(question);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id){
        questionRepository.delete(Util.findQuestionById(id, questionRepository));
        return "redirect:/";
    }
}
