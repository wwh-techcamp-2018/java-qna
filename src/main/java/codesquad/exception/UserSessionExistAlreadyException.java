package codesquad.exception;

public class UserSessionExistAlreadyException extends RedirectException {

    public UserSessionExistAlreadyException() {
        super("/");
    }
}
