package codesquad.exception;

import codesquad.domain.Alert;
import codesquad.exception.UserNotMatchException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(NoSessionedUserException.class)
    public String nullPoint(NoSessionedUserException nsue) {
        return nsue.getUrl();
    }

    @ExceptionHandler(UserNotMatchException.class)
    public String userNotMatch(UserNotMatchException ue, Model model) {
        model.addAttribute("alert", new Alert("다른 사람의 글을 변경할 수 없습니다.", ue.getUrl()));
        return "/qna/alert";
    }

    @ExceptionHandler(NullAnswerException.class)
    public String nullAnswerExption(NullAnswerException nae, Model model) {
        model.addAttribute("alert", new Alert("답변을 찾을 수 없습니다.", nae.getUrl()));
        return "/qna/alert";
    }

    @ExceptionHandler(NullQuestionException.class)
    public String nullQuestionExption(NullQuestionException nqe, Model model) {
        model.addAttribute("alert", new Alert("글을 찾을 수 없습니다.", nqe.getUrl()));
        return "/qna/alert";
    }
}
