package codesquad.domain;

import codesquad.exception.UnDeletableQuestionException;
import codesquad.util.DateUtils;
import codesquad.util.SessionUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;
    @Column(nullable = false)
    private String title;
    @Lob
    private String contents;
    @Column(nullable = false)
    private String time;
    @Column(nullable = false)
    private boolean deleted;

    @OneToMany(mappedBy = "question")
    @JsonIgnore
    @Where(clause = "deleted = false")
    @Cascade(value = org.hibernate.annotations.CascadeType.REMOVE)
    private List<Answer> answers = new ArrayList<>();

    public Question() {
    }

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.deleted = false;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean getDeleted() {
        return this.deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTime() {
        return time;
    }

    public void setTime() {
        this.time = DateUtils.getCurrentTime();
    }

    public void updateTime(String date) {
        this.time = time;
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

    public List<Answer> getAnswers() {
        return answers;
    }

    public void addAnswers(Answer answer) {
        this.answers.add(answer);
    }

    public Long getWriterId() {
        return writer.getId();
    }

    public Question update(Question modifiedQuestion) {
        setTitle(modifiedQuestion.getTitle());
        setContents(modifiedQuestion.getContents());

        return this;
    }

    // TODO 질문을 삭제할 수 있는지에 대한 여부를 여기서 확인하기
    // TODO 삭제를 담당하는 경우의 수가 많으니까 단위 테스트를 추가하라
    public void delete(User loginUser) {
        if(!isDeletable(loginUser)) {
            throw new UnDeletableQuestionException();
        }

        setDeleted(true);
        for (Answer answer : answers) {
            answer.delete();
        }
    }

    private boolean isDeletable(User user) {
        SessionUtils.getInstance().checkSameUser(user, getWriterId());
        boolean flag = true;
        for (Answer answer : answers) {
            flag = (flag && answer.isDeletable(user));
        }
        return flag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(id, question.id) &&
                Objects.equals(writer, question.writer) &&
                Objects.equals(title, question.title) &&
                Objects.equals(contents, question.contents) &&
                Objects.equals(time, question.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, writer, title, contents, time);
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", writer=" + writer +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
