package codesquad.web;

import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("/question")
    public String create(Question question) {
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("questions", questionRepository.findAll());
        return "/qna/index";
    }

    @GetMapping("/questions/{index}")
    public String show(@PathVariable int index, Model model) {
        model.addAttribute("question", questionRepository.findById(index).get());
        return "/qna/show";
    }

    @DeleteMapping("/questions/{index}")
    public String delete(@PathVariable int index, Model model) {
        questionRepository.deleteById(index);
        return "redirect:/";
    }

    @GetMapping("/questions/updateForm/{index}")
    public String getQuestionInfo(@PathVariable int index, Model model) {
        model.addAttribute("question", questionRepository.findById(index).get());
        return "qna/updateForm";
    }

    @PutMapping("questions/{index}")
    public String updateQuestionInfo(@PathVariable int index, Question updateQuestion, Model model) {
        Question question = questionRepository.findById(index).get();
        question.updateQuestion(updateQuestion);
        questionRepository.save(question);
        return "redirect:/questions/" + index;
    }

}
