package codesquad.dto.question;

import codesquad.domain.question.Answer;
import codesquad.domain.question.Question;
import codesquad.domain.user.User;

public class AnswerDto {
    private String contents;

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Answer toEntity(Question question, User writer) {
        return new Answer(question, writer, contents);
    }
}
