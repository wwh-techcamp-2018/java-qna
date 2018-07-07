package codesquad.web;

import java.util.Date;

//writer, title, contents
public class Question {
    private Date time;
    private String writer;
    private String title;
    private String contents;

    public Question(String writer, String title, String contents, Date time) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.time = new Date();
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public String getTime() {
        return time.toString();
    }
}
