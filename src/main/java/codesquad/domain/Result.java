package codesquad.domain;

public class Result {
    private Long answerId;
    private String message;
    private boolean success;

    public Result(Long answerId) {
        this.answerId = answerId;
    }

    public Result fail(String errorMessage) {
        this.message = errorMessage;
        this.success = false;
        return this;
    }

    public Result ok() {
        this.success = true;
        return this;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
