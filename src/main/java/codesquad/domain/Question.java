package codesquad.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int index;

    @Column(nullable=false)
    private String title;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Lob
    @Column(name = "contents", columnDefinition = "BLOB")
    private String contents;

    private Date date;

    public Question(){
        date = new Date();
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
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

    public void setUser(User user) {
        this.writer = user;
    }

    public void updateQuestion(Question updateQuestion, User currentUser) {
        if (!writer.equalsUser(currentUser))
            throw new IllegalArgumentException();

        title = updateQuestion.title;
        contents = updateQuestion.contents;
        date = updateQuestion.date;

    }
}
