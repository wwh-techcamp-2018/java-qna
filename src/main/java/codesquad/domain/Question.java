package codesquad.domain;

public class Question {
    private int id;
    private String writer;
    private String title;
    private String contents;
    private int point;

    public Question() { }

    public Question(String writer, String title, String contents, int point) {
        this.point = point;
        this.title = title;
        this.writer = writer;
        this.contents = contents;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }
}
