package codesquad.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Question {
    private String writer;
    private String title;
    private String contents;
    private String enrollTime;

    public Question(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.enrollTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
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

    public String getEnrollTime() {
        return enrollTime;
    }

    public void setEnrollTime(String enrollTime) {
        this.enrollTime = enrollTime;
    }
}
