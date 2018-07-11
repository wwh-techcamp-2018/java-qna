package codesquad.domain;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable=false)
    private String title;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Lob
    @Column(name = "contents", columnDefinition = "BLOB")
    private String contents;

    private Date date;

    @Column()
    @ColumnDefault(value = "false")
    private boolean deleted;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setDeleted() {
        this.deleted = true;
    }

    public boolean isDeletable(User currentUser, List<Answer> answers) {

        if(!writer.equalsUser(currentUser)) {
            return false;
        }

        for (Answer answer : answers) {

            if (!answer.isWriter(writer)) {
                return false;
            }
        }
        return true;
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
