package codesquad.web.exception;

import codesquad.domain.api.Result;
import codesquad.exception.RedirectableException;
import codesquad.exception.api.BaseApiException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RedirectableException.class)
    public String handleRedirectableException(RedirectableException exception) {
        return exception.getRedirectUrl();
    }

    @ExceptionHandler(BaseApiException.class)
    @ResponseBody
    public Result handleBaseApiException(BaseApiException exception) {
        return exception.getResult();
    }
}
