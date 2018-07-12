package codesquad.web;

import codesquad.exception.RedirectException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(RedirectException.class)
    public String handleRedirectException(RedirectException e) {
        return e.getRedirectUrl();
    }

}
