package codesquad.exception.user;

import codesquad.exception.RedirectableException;

public class UnauthorizedException extends RedirectableException {
    public UnauthorizedException() {
        super("redirect:/users/login");
    }
}
