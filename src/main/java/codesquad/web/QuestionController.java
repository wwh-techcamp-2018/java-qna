package codesquad.web;

import codesquad.domain.Question;
import codesquad.domain.User;
import codesquad.dto.QuestionDto;
import codesquad.exception.ForbiddenException;
import codesquad.exception.QuestionNotFoundException;
import codesquad.exception.RedirectableException;
import codesquad.exception.UnauthorizedException;
import codesquad.repository.QuestionRepository;
import codesquad.util.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.QueryTimeoutException;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("/questions")
    @Transactional
    public String question(QuestionDto dto, HttpSession session) {
        User user = AuthenticationUtil.getUserFromSession(session).orElseThrow(UnauthorizedException::new);
        questionRepository.save(dto.toEntity(user));
        return "redirect:/";
    }

    @GetMapping("/")
    public String showList(Model model) {
        model.addAttribute("questions", questionRepository.findAllByDeletedIsFalse());
        return "index";
    }

    @GetMapping("/questions/{id}")
    public String show(Model model, @PathVariable Long id) {
        model.addAttribute("question", findQuestionOrThrow(id));
        return "/qna/show";
    }

    @PutMapping("/questions/{id}")
    @Transactional
    public String update(QuestionDto dto, @PathVariable Long id, HttpSession session) {
        User user = AuthenticationUtil.getUserFromSession(session).orElseThrow(ForbiddenException::new);
        Question question = findQuestionOrThrow(id);
        question.update(user, dto);
        questionRepository.save(question);
        return "redirect:/";
    }

    @DeleteMapping("/questions/{id}")
    @Transactional
    public String delete(@PathVariable Long id, HttpSession session) {
        User user = AuthenticationUtil.getUserFromSession(session).orElseThrow(UnauthorizedException::new);
        Question question = findQuestionOrThrow(id);
        question.delete(user);
        return "redirect:/";
    }

    @GetMapping("/questions/{id}/form")
    public String openUpdateForm(@PathVariable Long id, Model model, HttpSession session) {
        AuthenticationUtil.getUserFromSession(session).orElseThrow(ForbiddenException::new);
        model.addAttribute("question", findQuestionOrThrow(id));
        return "/qna/updateForm";
    }

    @ExceptionHandler(RedirectableException.class)
    public String handleRedirectableException(RedirectableException exception) {
        return exception.getRedirectUrl();
    }

    private Question findQuestionOrThrow(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(QuestionNotFoundException::new);
    }
}
