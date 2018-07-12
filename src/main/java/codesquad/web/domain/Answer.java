package codesquad.web.domain;

import codesquad.web.exception.NoLoginException;

import javax.persistence.*;

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;
    private String contents;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_writer"))
    private User writer;
    private boolean deleted;

    public Answer(){
        this.deleted = false;
    }

    public Answer(Question question, String contents, User writer){
        this.question = question;
        this.contents = contents;
        this.writer = writer;
        this.deleted = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getContents() {
        return contents;
    }

    public User getWriter() {
        return writer;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public boolean matchWriter(User writer) {
        return this.writer.equals(writer);
    }

    public boolean delete() {
        if (this.deleted) {
            throw new IllegalArgumentException("이미 존재하지 않는 댓글입니다.");
        }
        setDeleted(true);
        return true;
    }

    public boolean delete(User writer) {
        if (!matchWriter(writer)) {
            return false;
        }
        return delete();
    }
}
