package codesquad.web;

import codesquad.domain.Result;
import codesquad.domain.exception.CustomAPIException;
import codesquad.domain.exception.CustomException;
import codesquad.domain.exception.CustomRedirectException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

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

    @ExceptionHandler(CustomAPIException.class)
    public @ResponseBody Map<String, Object>
    apiErrorHandling(CustomAPIException exception) {
        System.out.println("apiErrorHandling");
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("result", Result.fail(exception.getMessage()));
        return responseBody;
//        return "/error/error";
    }
}
