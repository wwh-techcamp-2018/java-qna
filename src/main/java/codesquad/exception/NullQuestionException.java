package codesquad.exception;

public class NullQuestionException extends RuntimeException {
    private String url = "/";

    public NullQuestionException(String msg) {
        super(msg);
    }

    public NullQuestionException() {
        this("null question");
    }

    public String getUrl() {
        return url;
    }
}
