package codesquad.domain.result;

import codesquad.domain.Answer;

public class AnswerResult {
    private String message;
    private Answer answer;

    public AnswerResult ok(Answer answer) {
        this.message = "ok";
        this.answer = answer;
        return this;
    }

    public AnswerResult fail(String message) {
        this.message = message;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
}
