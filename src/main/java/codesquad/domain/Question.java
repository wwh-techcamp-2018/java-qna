package codesquad.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;
    @Size(min = 1)
    @Column(nullable = false)
    private String title;
    @Lob
    private String contents;

    public Question() {
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

    public Long getId() {
        return id;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void update(Question target) throws UserNotMatchException {
        isWriter(target.getWriter());
        contents = target.getContents();
    }

    public void isWriter(User user) throws UserNotMatchException {
        if (!writer.equals(user)) {
            throw new UserNotMatchException();
        }
    }
}
