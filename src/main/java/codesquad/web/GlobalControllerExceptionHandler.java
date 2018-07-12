package codesquad.web;

import codesquad.exception.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    @ExceptionHandler(RedirectException.class)
    public String redirectException(RedirectException e) {
        return e.getRedirectUrl();
    }
}
