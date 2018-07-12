package codesquad.exception;

public class NullAnswerException extends RuntimeException {
    private String url = "/";

    public NullAnswerException() {
        this("null answer");
    }

    public NullAnswerException(String message) {
        super(message);
    }

    public String getUrl() {
        return this.url;
    }
}
