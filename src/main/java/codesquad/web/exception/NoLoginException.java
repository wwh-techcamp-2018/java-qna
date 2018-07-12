package codesquad.web.exception;

public class NoLoginException extends RuntimeException {
    public NoLoginException() {
        super();
    }

    public NoLoginException(String message) {
        super(message);
    }
}
