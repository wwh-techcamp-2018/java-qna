package codesquad.dto;

import org.springframework.http.HttpStatus;

public class AnswerDeleteResult {
    private HttpStatus httpStatus;
    private String msg;
    private Long answerId;


    public AnswerDeleteResult(HttpStatus httpStatus, String msg) {
        this(httpStatus, msg, null);
    }

    public AnswerDeleteResult(HttpStatus httpStatus, String msg, Long answerId) {
        this.httpStatus = httpStatus;
        this.msg = msg;
        this.answerId = answerId;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMsg() {
        return msg;
    }

    public Long getAnswerId() {
        return answerId;
    }
}
