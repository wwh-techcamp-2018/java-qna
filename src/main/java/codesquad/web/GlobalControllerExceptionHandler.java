package codesquad.web;

import codesquad.exception.RedirectableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    @ExceptionHandler(RedirectableException.class)
    public String handleRedirectableException(RedirectableException exception) {
        return exception.getRedirectPath();
    }
}
