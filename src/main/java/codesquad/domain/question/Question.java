package codesquad.domain.question;

import codesquad.domain.user.User;
import codesquad.exception.user.PermissionDeniedException;
import codesquad.exception.user.UserNotFoundException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Column(length = 100, nullable = false)
    private String title;

    @OneToMany(mappedBy = "question", cascade = CascadeType.PERSIST)
    @Where(clause = "deleted = false")
    @JsonIgnore
    private List<Answer> answers = new ArrayList<>();

    @Lob
    private String contents;

    @Column(nullable = false)
    private Integer comment;

    @Column(nullable = false)
    private boolean deleted = false;

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


    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public void delete(User user) {
        if (!matchWriter(user)) {
            throw new UserNotFoundException();
        }
        for (Answer answer : answers) {
            answer.delete(user);
        }
        deleted = true;
    }

}
