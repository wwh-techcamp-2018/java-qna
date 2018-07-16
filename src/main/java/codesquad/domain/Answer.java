package codesquad.domain;

import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Where(clause = "deleted != 'true'") // soft delete
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User answerWriter;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @Lob
    private String contents;

    @Column
    private String enrollTime;

    @Column(name = "deleted")
    private boolean isDeleted;

    public Answer() {
    }

    public Answer(User loginUser, Question question) {
        this.answerWriter = loginUser;
        this.question = question;
        enrollTime= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }


    public void create(User loginUser, Question question) {
        this.answerWriter = loginUser;
        this.question = question;
        enrollTime= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public void delete(User loginUser) {
        if(!loginUser.matchUserId(this.answerWriter.getUserId())) {
            throw new IllegalArgumentException("Other people's reply can not be deleted.");
        }
        isDeleted = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAnswerWriter() {
        return answerWriter;
    }

    public void setAnswerWriter(User answerWriter) {
        this.answerWriter = answerWriter;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
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

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", answerWriter=" + answerWriter +
                ", question=" + question +
                ", contents='" + contents + '\'' +
                ", enrollTime='" + enrollTime + '\'' +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
