package codesquad.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class Exceptionhandler {
    @ExceptionHandler(UnidentifiedUserException.class)
    public String unidentifiedUserExceptionHandler() {
        return "redirect:/users/login";
    }

    @ExceptionHandler(UnAuthorizedUserException.class)
    public String unAuthorizedUserExceptionHandler() {
        return "/error/error";
    }
}
