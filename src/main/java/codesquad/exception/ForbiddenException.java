package codesquad.exception;

public class ForbiddenException extends RedirectableException {

    public ForbiddenException() {
        super("redirect:/forbidden");
    }
}
