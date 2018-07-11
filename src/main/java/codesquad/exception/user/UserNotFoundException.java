package codesquad.exception.user;

import codesquad.exception.RedirectableException;

public class UserNotFoundException extends RedirectableException {

    public UserNotFoundException() {
        super("redirect:/users/login");
    }
}
