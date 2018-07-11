package codesquad.domain;

import codesquad.exception.ForbiddenException;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_user"))
    private User writer;

    @Lob
    private String contents;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Column(nullable = false)
    private boolean deleted;

    public Answer() {
        deleted = false;
    }

    public Answer(User writer, String contents) {
        this(null, writer, contents);
    }

    public Answer(Question question, User writer, String contents) {
        this();
        this.question = question;
        this.writer = writer;
        this.contents = contents;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public User getWriter() {
        return writer;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getContents() {
        return contents;
    }

    public boolean matchWriter(User user) {
        return writer.equals(user);
    }

    public boolean isDeletable(Question question) {
        return this.question.equals(question);
    }

    public void delete(Question question) {
        if (!isDeletable(question)) {
            throw new ForbiddenException();
        }

        deleted = true;
    }
}
