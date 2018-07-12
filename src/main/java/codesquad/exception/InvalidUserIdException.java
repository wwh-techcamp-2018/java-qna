package codesquad.exception;

public class InvalidUserIdException extends RedirectException {

    public InvalidUserIdException() {
        super("/users/login");
    }

}
