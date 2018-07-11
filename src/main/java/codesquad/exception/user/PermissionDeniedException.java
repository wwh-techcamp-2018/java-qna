package codesquad.exception.user;

import codesquad.exception.RedirectableException;

public class PermissionDeniedException extends RedirectableException {
    public PermissionDeniedException() {
        super("redirect:/error");
    }
}
