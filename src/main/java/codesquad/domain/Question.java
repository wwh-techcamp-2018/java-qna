package codesquad.domain;

import javax.persistence.*;
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

    public Question() {
    }

    public Question(User writer, String title, String contents) {
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

    public void update(Question question) {
        if (!matchId(question)) return;
        if (!matchWriter(question.writer.getId())) return;
        this.title = question.title;
        this.contents = question.contents;
    }

    public boolean matchId(Question question) {
        return id.equals(question.id);
    }

    public boolean matchWriter(Long id) {
        return this.writer.matchId(id);
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
