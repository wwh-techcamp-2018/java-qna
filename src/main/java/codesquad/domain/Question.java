package codesquad.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int index;

    @Column(length=30, nullable = false)
    private String writer;
    @Column(nullable=false)
    private String title;

    @Lob
    @Column(name = "CONTENTS", columnDefinition = "BLOB")
    private String contents;

    private Date date;

    public Question(){
        date = new Date();
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
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

    public long getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void updateQuestion(Question updateQuestion) {
        setTitle(updateQuestion.getTitle());
        setContents(updateQuestion.getContents());
        setWriter(updateQuestion.getWriter());
        setDate(updateQuestion.getDate());
    }
}
