package codesquad.web;

import codesquad.Exception.RedirectException;
import codesquad.domain.Question;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(RedirectException.class)
    public String RedirectExceptionHandler(Model model, RedirectException e) {
        log.error(e.getErrorMessage());
        model.addAttribute("error", e);
        return e.getRedirectUri();
    }
}
