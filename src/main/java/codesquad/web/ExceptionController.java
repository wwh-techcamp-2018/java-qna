package codesquad.web;

import codesquad.exception.RedirectException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(RedirectException.class)
    public String handleException(RedirectException exception) {
        return exception.getRedirectUrl();
    }
}
