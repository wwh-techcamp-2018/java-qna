package codesquad.dto.question;

import codesquad.domain.question.Question;
import codesquad.domain.user.User;

public class QuestionDto {
    private String title;
    private String contents;

    public QuestionDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Question toEntity(User writer) {
        return new Question(writer, title, contents);
    }
}
