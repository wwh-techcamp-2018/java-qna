package codesquad.domain.exception;

public class CustomAPIException extends RuntimeException {
    public CustomAPIException(String message) {
    super(message);
}
}
