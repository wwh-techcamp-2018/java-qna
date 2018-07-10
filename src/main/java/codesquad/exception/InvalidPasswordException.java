package codesquad.exception;

public class InvalidPasswordException extends RedirectException {
    public InvalidPasswordException() {
        super("/error/login_failed.html");
    }
}
