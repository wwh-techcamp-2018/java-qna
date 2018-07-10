package codesquad.exception;

public class InvalidLoginException extends RuntimeException {
    public InvalidLoginException() {
        super();
    }

    public InvalidLoginException(String message) {
        super(message);
    }
}
