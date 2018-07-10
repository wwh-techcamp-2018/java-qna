package codesquad.exception;

public class PasswordMismatchException extends RedirectableException {
    public PasswordMismatchException() {
        super("redirect:/users/me");
    }
}
