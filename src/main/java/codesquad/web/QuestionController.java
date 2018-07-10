package codesquad.web;

import codesquad.SessionUtil;
import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import codesquad.domain.User;
import codesquad.dto.question.QuestionDto;
import codesquad.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    QuestionRepository questionRepository;

    @PostMapping("")
    public String create(QuestionDto dto, HttpSession session) {
        questionRepository.save(dto.toEntity(SessionUtil.getMaybeUser(session).orElseThrow(NotFoundException::new)));
        return "redirect:/";
    }

    @GetMapping("")
    public String createQuestionForm() {
        return "qna/form";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable long id, Model model) {
        model.addAttribute("question", searchQuestion(id));
        return "/qna/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable long id, Model model) {
        model.addAttribute("question", searchQuestion(id));
        return "/qna/updateForm";
    }

    @PutMapping("/{id}")
    public String updateQuestion(@PathVariable long id, String contents, HttpSession session) {
        User loginedUser = SessionUtil.getMaybeUser(session).orElseThrow(NotFoundException::new);
        Question question = searchQuestion(id);
        question.setContents(contents);

        if (!question.matchWriter(loginedUser)) {
            throw new NotFoundException();
        }

        questionRepository.save(question);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable long id, HttpSession session) {
        User loginedUser = SessionUtil.getMaybeUser(session).orElseThrow(NotFoundException::new);
        Question question = searchQuestion(id);

        if (!question.matchWriter(loginedUser)) {
            throw new NotFoundException();
        }

        questionRepository.deleteById(id);
        return "redirect:/";
    }

    private Question searchQuestion(long id) {
        return questionRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @ExceptionHandler(NotFoundException.class)
    public String handleUserNotFoundException() {
        return "redirect:/error";
    }
}
