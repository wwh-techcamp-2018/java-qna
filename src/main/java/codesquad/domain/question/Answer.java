package codesquad.domain.question;

import codesquad.domain.user.User;
import codesquad.exception.user.PermissionDeniedException;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment primary key
    private Long id;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_writer"))
    private User writer;
    private String contents;

    @Column(nullable = false)
    private boolean deleted = false;

    @CreationTimestamp
    private LocalDateTime createdDate;

    public Answer(Question question, User writer, String contents) {
        this();
        this.question = question;
        this.writer = writer;
        this.contents = contents;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public Answer() {
        this.createdDate = LocalDateTime.now();
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

    public boolean matchWriter(User user) {
        return writer.equals(user);
    }

    public void delete(User user) {
        if (this.deleted) {
            return;
        }
        if (!matchWriter(user)) {
            throw new PermissionDeniedException();
        }
        deleted = true;
    }

    public boolean getDeleted() {
        return deleted;
    }
}
