package codesquad.domain.api;

import org.springframework.http.HttpStatus;

public class Result {
    private HttpStatus status;

    private String message;
    private Result(String message) {
        this.status = HttpStatus.OK;
        this.message = message;
    }


    private Result(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public static Result ok() {
        return new Result("ok");
    }

    public static Result fail(String errorMessage) {
        return new Result(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
    }

    public static Result fail(HttpStatus status, String errorMessage) {
        return new Result(status, errorMessage);
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
