package codesquad.web.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String title;
    @Lob
    private String contents;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;
    private boolean deleted = false;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers;

    public Question() {
    }

    public Question(String title, String contents, User writer) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.deleted = false;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
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

    public void setId(Long id) {
        this.id = id;
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

    public void update(Question question) {
        this.writer = question.getWriter();
        this.title = question.getTitle();
        this.contents = question.getContents();
    }

    public boolean deletable(User writer) {
        if (!this.writer.equals(writer)) {
            return false;
        }
        return this.answers
                .stream()
                .allMatch(answer -> answer.matchWriter(writer));
    }

    public void delete(User writer) {
        if (deletable(writer)) {
            setDeleted(true);
            this.answers.stream()
                    .forEach(answer -> answer.delete());
        }
    }
}
