package codesquad.common;

import codesquad.exception.UnAuthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public String error404(Model model, IllegalArgumentException e) {
        log.info("exception message : {}", e.getMessage());
        model.addAttribute("exception", e);
        return "/error/error404";
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public String loginView() {
        return "/user/login";
    }
}