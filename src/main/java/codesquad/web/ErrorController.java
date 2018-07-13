package codesquad.web;

import codesquad.service.CustomException;
import codesquad.service.CustomRedirectException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorController {
    @ExceptionHandler(CustomException.class)
    public String errorHandling(Model model, CustomException exception) {
        model.addAttribute("errorMessage", exception.getMessage());
        return "/error/error";
    }
    @ExceptionHandler(CustomRedirectException.class)
    public String redirectHandling(CustomRedirectException exception){
        return "redirect:/users/login/form";
    }
}
