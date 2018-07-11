package codesquad.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingController {
    @ExceptionHandler(InvalidLoginException.class)
    public String passLoginPage(Exception e, Model model) {
        model.addAttribute("errorMsg", e.getMessage());
        return "/user/login_failed";
    }

    @ExceptionHandler(UnAuthorizedDeleteException.class)
    public String passErrorPage(Exception e, Model model) {
        model.addAttribute("errorMsg", e.getMessage());
        return "/common/error";
    }
}
