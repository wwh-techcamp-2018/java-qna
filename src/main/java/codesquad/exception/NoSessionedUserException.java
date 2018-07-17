package codesquad.exception;

public class NoSessionedUserException extends RuntimeException {
    private String url = "/user/login";

    public NoSessionedUserException() {
        this("no sessioned user");
    }

    public NoSessionedUserException(String msg) {
        super(msg);
    }

    public String getUrl() {
        return url;
    }
}
