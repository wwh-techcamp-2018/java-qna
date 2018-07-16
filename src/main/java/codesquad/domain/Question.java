package codesquad.domain;

import codesquad.domain.exception.CustomErrorMessage;
import codesquad.domain.exception.CustomException;
import codesquad.web.ValidateUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Entity
@Where(clause = "deleted = false")
public class Question implements Serializable {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_user_id"), nullable = false)
    private User writer;

    @Column(nullable = false, length = 30, updatable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String contents;

    @Column(nullable = false, length = 30)
    private String writeTime;

    @OneToMany(mappedBy = "question")
    @PrimaryKeyJoinColumn
    @Where(clause = "deleted = false")
    @OrderBy("id ASC")
    @JsonIgnore
    private List<Answer> answers;

    @Column(nullable = false)
    private boolean deleted;

    @Column(nullable = false)
    private int answerCount;


    public Question() {
        answerCount = 0;
    }

    private Question(User writer, String title, String contents, String writeTime) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.writeTime = writeTime;
        answerCount = 0;
    }

    public static Question newQuestion(User writer, String title, String contents){
        if( !(ValidateUtils.validateString(title) && ValidateUtils.validateString(contents) && ValidateUtils.validateObject(writer))){
            throw new CustomException(CustomErrorMessage.BLANK);
        }
        return new Question(writer, title, contents, calculateWriteTime());
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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getWriteTime() {
        return writeTime;
    }

    public void setWriteTime(String writeTime) {
        this.writeTime = writeTime;
    }

    public List<Answer> getAnswers() {
        return Collections.unmodifiableList(answers);
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public int getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(int answerCount) {
        this.answerCount = answerCount;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", writer=" + writer +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", writeTime='" + writeTime + '\'' +
                ", answers=" + answers +
                '}';
    }

    public void updateData(Question question) {
        writer.matchUser(question.writer);
        this.contents = question.contents;
        this.title = question.title;
        this.answers = question.answers;
    }

    private static String calculateWriteTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return dtf.format(LocalDateTime.now());
    }

    public static void checkFields( String title, String contents) {
        if (contents == null || title == null || contents.equals("") || title.equals("")) {
            throw new CustomException(CustomErrorMessage.BLANK);
        }
    }

    public void addAnswer(Answer answer) {
        this.answers.add(answer);
        answer.setQuestion(this);
        answerCount++;
    }


    public boolean deletable() {
        return (answers.size() == 0) || checkAnswerWriter();
    }

    private boolean checkAnswerWriter() {
        return answers.stream().allMatch(answer -> answer.validateWriter(writer));
    }

    public void deleteQuestion(User user) {
        if ( !writer.equals(user) || !deletable())
            throw new CustomException(CustomErrorMessage.NOT_AUTHORIZED);
        answers.stream().filter(answer -> !(answer.isDeleted()))
                .forEach(restAnswer -> restAnswer.delete(writer, this));
        deleted = true;
        answerCount = 0;
    }

    public void deleteAnswer(){
        answerCount--;
    }

    public void checkDeleteAuthority(User sessionedUser) {
            if(!writer.equals(sessionedUser))
                throw new CustomException(CustomErrorMessage.NOT_AUTHORIZED);
    }



}
