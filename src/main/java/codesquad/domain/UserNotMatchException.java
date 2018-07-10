package codesquad.domain;

public class UserNotMatchException extends Exception {
    public UserNotMatchException() {
        this("user not match");
    }

    public UserNotMatchException(String msg) {
        super(msg);
    }
}
