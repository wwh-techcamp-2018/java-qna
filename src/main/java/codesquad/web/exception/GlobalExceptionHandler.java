package codesquad.web.exception;

import codesquad.web.domain.Result;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handlerException(Exception e, Model model){
        model.addAttribute("errorMessage", e.getMessage());
        return "common/errorPage";
    }

    @ExceptionHandler(NoLoginException.class)
    public String noLoginException(Exception e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "user/login_failed";
    }
}
