package codesquad.domain;

import codesquad.exception.ForbiddenDeleteQuestionException;

import javax.persistence.*;
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

    @Column(length = 30, nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String contents;

    // answer repository 를 만들지 않아도 cascade 설정만으로 crud 가능!
    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
    //@OrderBy("answerId DESC")
    //@JsonIgnore
    private List<Answer> answers;

    @Column
    private boolean deleted = false;


    public Question() {
    }

    public Question(User writer, String title, String contents) {
        this(writer, title, contents, null);
    }

    public Question(User writer, String title, String contents, List<Answer> answers) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.answers = answers;
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

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void update(Question question) {
        if (!matchId(question)) return;
        if (!matchWriter(question.writer.getId())) return;
        this.title = question.title;
        this.contents = question.contents;
    }

    public boolean matchId(Question question) {
        return id.equals(question.id);
    }

    public boolean matchWriter(Long writerId) {
        return this.writer.matchId(writerId);
    }

    public boolean hasAnswer() {
        if (answers.size() == 0) return false;
        return true;
    }

    public boolean matchQuestionWriterAndAllAnswerWriter() {
        if (answers.size() == answers.stream()
                .filter(answer -> answer.matchWriter(writer.getId()))
                .count())
            return true;
        return false;
    }

    public boolean canDelete(Long sessionId) {
        if (!matchWriter(sessionId))
            return false;
        if (hasAnswer() && !matchQuestionWriterAndAllAnswerWriter())
            return false;
        return true;
    }

    public void delete(Long loginUserId) {
        if (!canDelete(loginUserId))
            throw new ForbiddenDeleteQuestionException();
        answers.stream()
                .forEach(answer -> answer.setDeleted(true));
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
                Objects.equals(title, question.title) &&
                Objects.equals(contents, question.contents) &&
                Objects.equals(answers, question.answers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, writer, title, contents, answers, deleted);
    }
}
