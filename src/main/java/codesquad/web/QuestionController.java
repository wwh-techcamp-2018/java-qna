package codesquad.web;

import codesquad.domain.Question;
import codesquad.dto.QuestionDto;
import codesquad.exception.QuestionNotFoundException;
import codesquad.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.QueryTimeoutException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("/questions")
    public String question(QuestionDto dto) {
        questionRepository.save(dto.toEntity());
        return "redirect:/";
    }

    @GetMapping("/")
    public String showList(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }

    @GetMapping("/questions/{id}")
    public String show(Model model, @PathVariable Long id) {
        model.addAttribute("question", findQuestionOrThrow(id));
        return "/qna/show";
    }

    @PutMapping("/questions/{id}")
    public String update(QuestionDto dto, @PathVariable Long id) {
        Question question = findQuestionOrThrow(id);
        question.update(dto);
        questionRepository.save(question);
        return "redirect:/";
    }

    @DeleteMapping("/questions/{id}")
    public String delete(@PathVariable Long id) {
        questionRepository.delete(findQuestionOrThrow(id));
        return "redirect:/";
    }

    @GetMapping("/questions/{id}/form")
    public String openUpdateForm(@PathVariable Long id, Model model) {
        model.addAttribute("question", findQuestionOrThrow(id));
        return "/qna/updateForm";
    }

    @ExceptionHandler(QuestionNotFoundException.class)
    public String questionNotFoundRedirect() {
        return "redirect:/questions";
    }

    private Question findQuestionOrThrow(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(QuestionNotFoundException::new);
    }
}
