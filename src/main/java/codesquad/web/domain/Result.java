package codesquad.web.domain;

public class Result {
    String answerId;
    String message;
    String status;

    public Result(String answerId, String message, String status) {
        this.answerId = answerId;
        this.message = message;
        this.status = status;
    }

    public static Result ok(String answerId) {
        return new Result(answerId, "success", "200");
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public String getAnswerId() {
        return answerId;
    }
}

