package codesquad.exception;

public class RedirectException extends RuntimeException {

    private static final String REDIRECT_TO = "redirect:";
    private String redirectUrl;

    public RedirectException() {
        this(REDIRECT_TO);
    }

    public RedirectException(String redirectUrl) {
        this.redirectUrl = REDIRECT_TO + redirectUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

}
