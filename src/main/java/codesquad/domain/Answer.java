package codesquad.domain;

import codesquad.exception.AuthorizationException;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_to_answer"))
    private Question question;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_user_to_answer"))
    private User writer;

    @Lob
    private String contents;

    @CreationTimestamp
    private Date date;

    @Column
    private boolean deleted = false;


    public Answer(User writer, boolean deleted) {
        this.writer = writer;
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean validateWriter(User user) {
        return this.getWriter().equals(user);
    }

    public void delete(User user) {
        if(!this.validateWriter(user)){
            throw new AuthorizationException();
        }
        this.setDeleted(true);
    }
}
