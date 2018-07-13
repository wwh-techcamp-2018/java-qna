package codesquad.exception.user;

import codesquad.exception.RedirectableException;

public class PasswordMismatchException extends RedirectableException {
    public PasswordMismatchException() {
        super("redirect:/users/me");
    }
}
