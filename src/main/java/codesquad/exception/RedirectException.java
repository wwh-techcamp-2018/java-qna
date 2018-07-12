package codesquad.exception;

public class RedirectException extends RuntimeException {

    private static final String REDIRECT_TO = "redirect:";

    private String redirectUrl;

    public RedirectException() {
        this("/");
    }

    public RedirectException(String url) {
        this.redirectUrl = REDIRECT_TO + url;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

}
