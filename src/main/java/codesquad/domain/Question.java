package codesquad.domain;

import codesquad.service.CustomErrorMessage;
import codesquad.service.CustomException;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Entity
@Where(clause = "deleted = false")
public class Question {
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

    @OneToMany(mappedBy="question", cascade = CascadeType.MERGE)
    @PrimaryKeyJoinColumn
    @Where(clause = "deleted = false")
    @OrderBy("id ASC")
    private List<Answer> answers;

    @Column(nullable = false)
    private boolean deleted;


    public Question() {
        this(null,"defaultTitle","defaultContents");
    }

    public Question(User writer, String title, String contents) {
        checkFields(title, contents);
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.writeTime = calculateWriteTime();
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

    public int getAnswerCount(){
        return this.answers.size();
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
        if(!writer.matchUserId(question.writer))
            throw new CustomException(CustomErrorMessage.NOT_AUTHORIZED);
        this.contents = question.contents;
        this.title = question.title;
        this.answers = question.answers;
    }

    public boolean unEqualWriter(User user) {
        return !writer.equals(user);
    }

    private String calculateWriteTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return dtf.format(LocalDateTime.now());
    }

    public static void checkFields(String title, String contents){
        if(contents == null || title == null || contents.equals("") || title.equals("")) {
            throw new CustomException(CustomErrorMessage.BLANK);
        }
    }

    public void addAnswer(Answer answer){
        this.answers.add(answer);
        answer.setQuestion(this);
    }

//    public void deleteAnswer(long answerId){
////        Answer answer = answers.stream().filter(a -> a.isMatchedId(answerId)).findFirst().get();
////        //answer.setWriter(null);
////        answer.setQuestion(null);
////        answers.removeIf(a -> a.isMatchedId(answerId));
////    }

    public boolean isDeleted() {
        return deleted;
    }

    public boolean deletable(){
        return (answers.size() == 0) || checkAnswerWriter();
    }

    private boolean checkAnswerWriter(){
        return answers.stream().allMatch(answer -> answer.validateWriter(writer));
    }

    public void deleteQuestion(User user){
        if(unEqualWriter(user) || !deletable())
            throw new CustomException(CustomErrorMessage.NOT_AUTHORIZED);
        answers.stream().filter(answer -> !(answer.isDeleted()))
                .forEach(restAnswer -> restAnswer.delete(writer));
        deleted = true;
    }

}
