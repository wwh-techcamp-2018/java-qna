package codesquad.exception;

public class UserNotInSessionException extends RedirectException {

    public UserNotInSessionException() {
        super("/users/login");
    }

}
