package codesquad.web;

import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import jdk.nashorn.internal.runtime.QuotedStringTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/questions")
public class QnaController {
    public static List<Question> questions = new ArrayList<>();

    @Autowired
    QuestionRepository questionRepository;

    @PostMapping
    public String create(Question question) {
        if (addQuestion(question))
            return "redirect:/";

        return "redirect:/error" + "/{}";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable long id, Model model) {
        model.addAttribute("question", questionRepository.findById(id).get());
        return "/qna/show";
    }

    @GetMapping("/{id}/form")
    public String viewForUpdate(@PathVariable long id, Model model) {
        model.addAttribute("question", questionRepository.findById(id).get());
        return "/qna/updateForm";
    }

    @PutMapping("/{id}")
    public String updateQuestion(@PathVariable long id, Question question) {
        Question targetQuestion = questionRepository.findById(id).get();
        question.setId(targetQuestion.getId());
        question.setWriteTime(targetQuestion.getWriteTime());
        questionRepository.save(question);
        return "redirect:/questions/{id}";
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable long id) {
        questionRepository.deleteById(id);
        return "redirect:/";
    }

    private boolean addQuestion(Question question) {
        questionRepository.save(question);
        questions.add(question);
        return true;
    }
}
