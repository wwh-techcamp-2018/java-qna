package codesquad.domain;

import codesquad.exception.AuthorizationException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date time;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Column(length = 30, nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String contents;


    @JsonIgnore
    @Where(clause = "deleted = false")
    @OrderBy("id ASC")
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answers;

    @Column
    private boolean deleted = false;

    public Question() {
        this.time = new Date();
    }

    public Question(User writer, String title, String contents, Date time) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.time = new Date();
    }

    public Question modify(Question q) {
        this.setWriter(q.writer);
        this.setTitle(q.title);
        this.setContents(q.contents);

        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public User getWriter() {
        return this.writer;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
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

    public boolean validateWriter(User user) {
        if(!this.getWriter().equals(user)){
            throw new AuthorizationException();
        }
        return true;
    }

    boolean isNotDeletable() {
        return this.getAnswers().stream()
                .filter(t -> !t.validateWriter(this.getWriter()))
                .anyMatch(Answer::isDeleted);
    }

    public void delete(User user) {
        if(isNotDeletable())
            throw new AuthorizationException();

        for (Answer answer : this.getAnswers())
            answer.delete(user);

        this.setDeleted(true);
    }
}
