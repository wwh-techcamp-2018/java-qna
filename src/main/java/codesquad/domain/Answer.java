package codesquad.domain;

import codesquad.exception.ForbiddenException;

import javax.persistence.*;
import java.util.Objects;
import java.util.Optional;

@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey)
    private Question question;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey)
    private User writer;

    @Column(nullable = false, length = 140)
    private String comment;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean isDeleted;

    public Answer() {

    }

    public Answer(Long id, Question question, User writer, String comment, boolean isDeleted) {
        this.id = id;
        this.question = question;
        this.writer = writer;
        this.comment = comment;
        this.isDeleted = isDeleted;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Answer deleteByUser(User user) {
        validateWriter(user);
        this.isDeleted = true;
        return this;
    }

    public boolean isWriterMatch(User other) {
        return this.writer.equals(other);
    }


    public void validateWriter(User user) {
        if (!isWriterMatch(user)) {
            throw new ForbiddenException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return isDeleted == answer.isDeleted &&
                Objects.equals(id, answer.id) &&
                Objects.equals(question, answer.question) &&
                Objects.equals(writer, answer.writer) &&
                Objects.equals(comment, answer.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, question, writer, comment, isDeleted);
    }

}
