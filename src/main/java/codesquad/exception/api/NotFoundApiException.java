package codesquad.exception.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class NotFoundApiException extends BaseApiException {
    public NotFoundApiException() {
        super(HttpStatus.NOT_FOUND, "Not Found");
    }
}
