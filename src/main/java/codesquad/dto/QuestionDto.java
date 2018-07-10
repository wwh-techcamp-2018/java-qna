package codesquad.dto;

import codesquad.domain.Question;
import codesquad.domain.User;

public class QuestionDto {
    private String title;
    private String contents;

    public QuestionDto() {
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

    public Question toEntity(User user) {
        return new Question(user, title, contents);
    }
}
