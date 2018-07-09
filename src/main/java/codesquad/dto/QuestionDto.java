package codesquad.dto;

import codesquad.domain.Question;

public class QuestionDto {
    private String writer;
    private String title;
    private String contents;

    public QuestionDto() {
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
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

    public Question toEntity() {
        return new Question(writer, title, contents);
    }
}
