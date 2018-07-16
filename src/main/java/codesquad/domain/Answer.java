package codesquad.domain;

import codesquad.util.DateUtils;
import codesquad.util.SessionUtils;

import javax.persistence.*;

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String time;
    @Lob
    private String contents;
    @Column(nullable = false)
    private boolean deleted;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_parent_user_id"))
    private User writer;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_parent_question_id"))
    private Question question;

    public Answer() {
    }

    public Answer(User writer, String contents) {
        this.writer = writer;
        this.contents = contents;
        this.time = DateUtils.getCurrentTime();
        this.deleted = false;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
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

    public String getTime() {
        return time;
    }

    public void setTime() {
        this.time = DateUtils.getCurrentTime();
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Long getWriterId() {
        return this.writer.getId();
    }

    public void update(User user, Question question) {
        setTime();
        setWriter(user);
        setQuestion(question);
    }

    // TODO getWriterId() 가 질문을 쓴 사람과 아이디가 같으면 삭제, 아니면 냅두기

    public void delete(User loginUser) {
        if(!isDeletable(loginUser)) return;
        delete();
    }

    public void delete() {
        this.deleted = true;
    }

    public boolean isDeletable(User loginUser) {
        SessionUtils.getInstance().checkSameUserFromApi(loginUser, getWriterId());
        return true;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", time='" + time + '\'' +
                ", contents='" + contents + '\'' +
                ", deleted=" + deleted +
                ", writer=" + writer +
                ", question=" + question +
                '}';
    }
}
