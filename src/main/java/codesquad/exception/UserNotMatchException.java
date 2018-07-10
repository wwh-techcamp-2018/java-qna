package codesquad.exception;

public class UserNotMatchException extends RedirectException {

    public UserNotMatchException() {
        super("/error/error.html");
    }
}
