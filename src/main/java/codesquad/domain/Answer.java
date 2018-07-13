package codesquad.domain;

import codesquad.service.CustomErrorMessage;
import codesquad.service.CustomException;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Answer {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String writeTime;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_user_id"), nullable = false)
    private User writer;

    @Lob
    @Column(nullable = false, length = 200)
    private String contents;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @Column(nullable = false)
    private boolean deleted;

    public Answer(User writer, String contents) {
        this.writer = writer;
        this.contents = contents;
        writeTime = calculateWriteTime();
    }

    public Answer(String writeTime, User writer, String contents, Question question) {
        this.writeTime = writeTime;
        this.writer = writer;
        this.contents = contents;
        this.question = question;
    }

    private String calculateWriteTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return dtf.format(LocalDateTime.now());
    }


    public Answer(){}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWriteTime() {
        return writeTime;
    }

    public void setWriteTime(String writeTime) {
        this.writeTime = writeTime;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Question getQuestion() {
        return question;
    }
    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", writeTime='" + writeTime + '\'' +
                ", writer=" + writer +
                ", contents='" + contents + '\'' +
                ", deleted=" + deleted +
                '}';
    }

    public boolean isMatchedId(long answerId){
        return this.id == answerId;
    }
    public void delete(User sessionedUser){
        if(deleted)
            throw new CustomException(CustomErrorMessage.INCORRECT_ACCESS);
        if(!validateWriter(sessionedUser)){
            throw new CustomException(CustomErrorMessage.NOT_AUTHORIZED);
        }
        this.deleted = true;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public boolean validateWriter(User user){
        return writer.equals(user);
    }
}
