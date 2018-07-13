package codesquad.web.question;

import codesquad.SessionUtil;
import codesquad.domain.question.Question;
import codesquad.domain.question.QuestionRepository;
import codesquad.domain.user.User;
import codesquad.dto.question.QuestionDto;
import codesquad.exception.user.UserNotFoundException;
import codesquad.exception.user.PermissionDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    QuestionRepository questionRepository;

    @PostMapping("")
    @Transactional
    public String create(QuestionDto dto, HttpSession session) {
        questionRepository.save(dto.toEntity(SessionUtil.getUser(session)));
        return "redirect:/";
    }

    @GetMapping("")
    public String createQuestionForm() {
        return "qna/form";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable long id, Model model) {
        Question question = searchQuestion(id);
        model.addAttribute("question", question);
        model.addAttribute("answer", question.getAnswers());
        return "/qna/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable long id, Model model) {
        model.addAttribute("question", searchQuestion(id));
        return "/qna/updateForm";
    }

    @PutMapping("/{id}")
    @Transactional
    public String updateQuestion(@PathVariable long id, String contents, HttpSession session) {
        User loginedUser = SessionUtil.getMaybeUser(session).orElseThrow(PermissionDeniedException::new);
        Question question = searchQuestion(id);
        question.setContents(contents);

        if (!question.matchWriter(loginedUser)) {
            throw new PermissionDeniedException();
        }

        questionRepository.save(question);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    @Transactional
    public String deleteQuestion(@PathVariable long id, HttpSession session) {
        User loginedUser = SessionUtil.getUser(session);
        Question question = searchQuestion(id);

        question.delete(loginedUser);

        questionRepository.save(question);
        return "redirect:/";
    }

    private Question searchQuestion(long id) {
        return questionRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }
}
