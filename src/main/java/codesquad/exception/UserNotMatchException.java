package codesquad.exception;

public class UserNotMatchException extends RuntimeException {
    private String url;

    public UserNotMatchException() {
        this("user not match", "/");
    }

    public UserNotMatchException(String url) {
        this("user not match", url);
    }

    public UserNotMatchException(String msg, String url) {
        super(msg);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
