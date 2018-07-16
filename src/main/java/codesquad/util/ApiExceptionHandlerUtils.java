package codesquad.util;

import codesquad.domain.result.AnswerResult;
import codesquad.exception.ApiException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandlerUtils {

    @ExceptionHandler(ApiException.class)
    public AnswerResult apiExceptionHandler() {
        return (new AnswerResult().fail("Handler Fail"));
    }
}
