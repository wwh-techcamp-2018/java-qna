package codesquad.domain;

import codesquad.dto.QuestionDto;
import codesquad.exception.ForbiddenException;
import codesquad.exception.UnauthorizedException;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Where(clause = "deleted = false")
    private List<Answer> answers = new ArrayList<>();

    @Column(length = 50, nullable = false)
    private String title;
    @Lob
    private String contents;

    @Column(nullable = false)
    private boolean deleted;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdDate;

    public void addAnswer(Answer answer) {
        answer.setQuestion(this);
        answers.add(answer);
    }

    public Question() {
        this.deleted = false;
    }

    public Question(User writer, String title, String contents) {
        this();
        this.writer = writer;
        this.title = title;
        this.contents = contents;
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

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void update(User user, QuestionDto questionDto) {
        if (!matchWriter(user)) {
            throw new ForbiddenException();
        }
        this.title = questionDto.getTitle();
        this.contents = questionDto.getContents();
    }

    public boolean matchWriter(User user) {
        return writer.equals(user);
    }

    public boolean isDeletable(User user) {
        if (!matchWriter(user)) return false;

        if (answers.isEmpty()) return true;

        return answers.stream().allMatch(answer -> answer.matchWriter(user));
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void delete(User user) {
        if (!isDeletable(user)) throw new ForbiddenException();

        answers.forEach(answer -> answer.delete(this));
        deleted = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return deleted == question.deleted &&
                Objects.equals(id, question.id) &&
                Objects.equals(writer, question.writer) &&
                Objects.equals(answers, question.answers) &&
                Objects.equals(title, question.title) &&
                Objects.equals(contents, question.contents) &&
                Objects.equals(createdDate, question.createdDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, writer, answers, title, contents, deleted, createdDate);
    }
}
