package codesquad.domain;

import codesquad.exception.ForbiddenException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column
    private String contents;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean isDeleted;

    public Question() {

    }

    public Question(Long id, User writer, String title, String contents, boolean isDeleted) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.isDeleted = isDeleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public void updateByUser(User user, Question newQuestion) {
        validateWriter(user);
        this.title = newQuestion.title;
        this.contents = newQuestion.contents;
    }

    public void deleteByUser(User user, List<Answer> answers) {
        validateWriter(user);
        validateAnswers(answers);
        this.isDeleted = true;
    }

    public boolean isWriterMatch(User other) {
        return this.writer.equals(other);
    }

    public void validateWriter(User user) {
        if (!isWriterMatch(user)) {
            throw new ForbiddenException();
        }
    }

    public void validateAnswers(List<Answer> answers) {
        Optional.ofNullable(answers).orElseGet(ArrayList::new).stream().forEach(answer -> {
            if (!answer.isWriterMatch(this.writer) && !answer.isDeleted()) {
                throw new ForbiddenException();
            }
        });
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(id, question.id) &&
                Objects.equals(writer, question.writer) &&
                Objects.equals(title, question.title) &&
                Objects.equals(contents, question.contents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, writer, title, contents);
    }

}
