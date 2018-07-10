package codesquad.domain;

import javax.persistence.*;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey =  @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Column(nullable = false, length = 40)
    private String title;
    private String content;

    public Question() {
    }

    public Question(User writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Question updateQuestion(Question question) {
        this.title = question.title;
        this.content = question.content;
        return this;
    }

    public boolean matchWriter(String userId) {
        return this.writer.matchUserId(userId);
    }
}
