package codesquad.domain;

import javax.persistence.*;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name="fk_question_writer"))
    private User writer;
    @Column(length = 100, nullable = false)
    private String title;

    @Lob
    private String contents;

    @Column(nullable = false)
    private Integer comment;

    public Question() {
        comment = 0;
    }

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.comment = 0;
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

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Integer getComment() {
        return comment;
    }

    public boolean matchWriter(User user) {
        return writer.equals(user);
    }

}
