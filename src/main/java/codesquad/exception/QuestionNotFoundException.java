package codesquad.exception;

public class QuestionNotFoundException extends RedirectException {

    public QuestionNotFoundException() {
        super("/questions");
    }

}
