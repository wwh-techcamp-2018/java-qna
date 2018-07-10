package codesquad.exception;

public class QuestionNotFoundException extends RedirectableException {
    public QuestionNotFoundException() {
        super("redirect:/");
    }
}
