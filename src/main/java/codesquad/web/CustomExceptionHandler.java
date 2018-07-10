package codesquad.web;

import codesquad.domain.Alert;
import codesquad.domain.UserNotMatchException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(NullPointerException.class)
    public String nullPoint() {
        return "/user/login";
    }

    @ExceptionHandler(UserNotMatchException.class)
    public String userNotMatch(Model model) {
        model.addAttribute("alert", new Alert("다른 사람의 글을 변경할 수 없습니다.", "/"));
        return "/qna/alert";
    }
}
