package codesquad.Exception;

public class RedirectException extends RuntimeException {
    protected String errorMessage;
    protected String redirectUri;

    public RedirectException(String errorMessage) {
        this.errorMessage = errorMessage;
        this.redirectUri = "/error";
    }

    public RedirectException(String errorMessage, String redirectUri) {
        this.errorMessage = errorMessage;
        this.redirectUri = redirectUri;
    }


    public String getErrorMessage() {
        return errorMessage;
    }

    public String getRedirectUri() {
        return redirectUri;
    }
}
