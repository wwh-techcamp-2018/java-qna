package codesquad.web;

import codesquad.exception.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    //@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UserNotFoundException.class)
    public String userNotFound(RedirectException e) {
        return e.getRedirectUrl();
    }

    @ExceptionHandler(UserSessionNotFoundException.class)
    public String userSessionNotFound(RedirectException e) {
        return e.getRedirectUrl();
    }

    @ExceptionHandler(QuestionNotFoundException.class)
    public String questionnNotFound(RedirectException e) {
        return e.getRedirectUrl();
    }

    @ExceptionHandler(UserNotMatchException.class)
    public String userNotMatch(RedirectException e) {
        return e.getRedirectUrl();
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public String invalidPassword(RedirectException e) {
        return e.getRedirectUrl();
    }
}
