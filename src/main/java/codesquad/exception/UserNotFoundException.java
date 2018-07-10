package codesquad.exception;

public class UserNotFoundException extends RedirectException {

    public UserNotFoundException() {
        super("/users");
    }

}
