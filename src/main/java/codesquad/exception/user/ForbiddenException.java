package codesquad.exception.user;

import codesquad.exception.RedirectableException;

public class ForbiddenException extends RedirectableException {

    public ForbiddenException() {
        super("redirect:/forbidden");
    }
}
