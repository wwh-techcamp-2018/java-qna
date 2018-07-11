package codesquad.exception;

public class RedirectableException extends RuntimeException {
    private String redirectPath;

    public RedirectableException(String redirectPath) {
        this.redirectPath = redirectPath;
    }

    public String getRedirectPath() {
        return redirectPath;
    }
}
