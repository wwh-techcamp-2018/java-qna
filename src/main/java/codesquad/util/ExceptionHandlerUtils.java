package codesquad.util;

import codesquad.exception.UnAuthorizedUserException;
import codesquad.exception.UnDeletableQuestionException;
import codesquad.exception.UnidentifiedUserException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerUtils {
    @ExceptionHandler(UnidentifiedUserException.class)
    public String unidentifiedUserExceptionHandler() {
        return "redirect:/users/login";
    }

    @ExceptionHandler(UnAuthorizedUserException.class)
    public String unAuthorizedUserExceptionHandler() {
        return "/error/error";
    }

    @ExceptionHandler(UnDeletableQuestionException.class)
    public String unDeletableQuestionExceptionHandler() {
        return "/error/error";
    }

}
