package codesquad.exception;

public class InvalidPasswordException extends RedirectException {

    public InvalidPasswordException() {
        super("/users/login");
    }

    public InvalidPasswordException(String url) {
        super(url);
    }

}
