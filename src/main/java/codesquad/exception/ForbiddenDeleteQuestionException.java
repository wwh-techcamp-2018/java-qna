package codesquad.exception;

public class ForbiddenDeleteQuestionException extends RedirectException {
    public ForbiddenDeleteQuestionException() {
        super("/error/error.html");
    }
}
