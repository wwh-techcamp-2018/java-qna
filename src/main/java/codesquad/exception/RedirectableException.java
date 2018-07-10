package codesquad.exception;

public class RedirectableException extends RuntimeException {
    protected String redirectUrl;

    public RedirectableException() {
        this.redirectUrl = "/";
    }

    public RedirectableException(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }
}
