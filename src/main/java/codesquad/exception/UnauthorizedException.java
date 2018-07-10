package codesquad.exception;

public class UnauthorizedException extends RedirectableException {
    public UnauthorizedException() {
        super("redirect:/users/login");
    }
}
