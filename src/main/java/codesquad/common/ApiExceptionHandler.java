package codesquad.common;

import codesquad.dto.AnswerDeleteResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice("codesquad.api")
public class ApiExceptionHandler {
    private static final String RESULT_ERROR_MESSAGE = "error message";

    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    public AnswerDeleteResult error(Model model, IllegalArgumentException e) {
        log.info("exception message : {}", e.getMessage());
        return new AnswerDeleteResult(HttpStatus.FORBIDDEN, RESULT_ERROR_MESSAGE);
    }
}
