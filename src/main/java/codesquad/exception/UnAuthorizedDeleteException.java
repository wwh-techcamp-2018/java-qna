package codesquad.exception;

public class UnAuthorizedDeleteException extends RuntimeException {
    public UnAuthorizedDeleteException() {
        super();
    }

    public UnAuthorizedDeleteException(String message) {
        super(message);
    }
}
