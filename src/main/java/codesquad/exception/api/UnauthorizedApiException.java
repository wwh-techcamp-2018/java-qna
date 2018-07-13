package codesquad.exception.api;

import org.springframework.http.HttpStatus;

public class UnauthorizedApiException extends BaseApiException {
    public UnauthorizedApiException() {
        super(HttpStatus.UNAUTHORIZED, "Unauthorized");
    }
}
