package codesquad.web.exception;

import codesquad.domain.api.Result;
import codesquad.exception.api.BaseApiException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BaseApiException.class)
    public ResponseEntity<Object> handleBaseApiException(BaseApiException exception) {
        Result result = exception.getResult();
        return new ResponseEntity<>(result, new HttpHeaders(), result.getStatus());
    }
}
