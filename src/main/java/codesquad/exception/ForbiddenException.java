package codesquad.exception;

public class ForbiddenException extends RedirectException {

    public ForbiddenException() {
        super("/forbidden");
    }

}
