package codesquad.exception.question;

import codesquad.exception.RedirectableException;

public class QuestionNotFoundException extends RedirectableException {

    public QuestionNotFoundException() {
        super("redirect:/");
    }
}
