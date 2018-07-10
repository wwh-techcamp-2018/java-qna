package codesquad.exception;

public class UserNotFoundException extends RedirectableException {
    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String redirectUrl) {
        super(redirectUrl);
    }
}
