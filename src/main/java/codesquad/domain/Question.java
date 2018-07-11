package codesquad.domain;

import codesquad.exception.UnAuthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Entity
@Where(clause = "deleted != 'true'") // soft delete
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Column
    private String title;

    @Lob
    private String contents;
    @Column
    private String enrollTime;

    @Column(name = "deleted")
    private boolean isDeleted;

    @OneToMany(mappedBy = "question", cascade = CascadeType.PERSIST)
    private List<Answer> answers = new ArrayList<>();

    // 디폴트 생성자 필수
    public Question() {
    }

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.enrollTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public void create(User loginUser) {
        this.writer = loginUser;
        this.enrollTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public void update(User loginUser, Question updatedQuestion) {
        if (loginUser == null || !loginUser.matchUserId(writer.getUserId())) {
            throw new IllegalArgumentException("Other people's posts can not be edited.");
        }
        this.title = updatedQuestion.getTitle();
        this.contents = updatedQuestion.getContents();
    }

    public void createAnswer(User loginUser, Answer newAnswer) {
        newAnswer.create(loginUser, this);
        this.answers.add(newAnswer);
    }

    public void deleteAnswer(User loginUser, Answer answer) {
        answer.delete(loginUser);
    }

    public void delete(User loginUser) {
        if (loginUser == null || !loginUser.matchUserId(writer.getUserId())) {
            throw new UnAuthorizedException();
        }

        for (Answer answer : answers) {
            deleteAnswer(loginUser, answer);
        }
        isDeleted = true;
    }

    public boolean isMatchByUserId(User loginUser) {
        return (loginUser != null && loginUser.matchUserId(this.writer.getUserId()));
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

    public String getEnrollTime() {
        return enrollTime;
    }

    public void setEnrollTime(String enrollTime) {
        this.enrollTime = enrollTime;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public List<Answer> getAnswers() {
        return answers;
    }
}
