package codesquad.exception.api;

import codesquad.domain.api.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public abstract class BaseApiException extends HttpStatusCodeException {
    private Result result;

    BaseApiException(String message) {
        this(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    BaseApiException(HttpStatus status, String message) {
        super(status);
        this.result = Result.fail(status, message);
    }

    public Result getResult() {
        return result;
    }
}
