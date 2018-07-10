package codesquad.exception;

public class UserSessionNotFoundException extends RedirectException {

    public UserSessionNotFoundException() {
        super("/users/login");
    }

}
