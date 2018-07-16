package codesquad.domain;

import codesquad.exception.UnAuthorizedDeleteException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@Where(clause = "deleted = false")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey =  @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Column(nullable = false, length = 40)
    private String title;

    private String contents;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    @Where(clause = "deleted = false")
    @OrderBy("id ASC")
    @JsonIgnore
    private List<Answer> answers;

    private boolean deleted = false;

    public Question() {
    }

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public Question updateQuestion(Question question) {
        this.title = question.title;
        this.contents = question.contents;
        return this;
    }

    public boolean matchWriter(String userId) {
        return this.writer.matchUserId(userId);
    }

    public boolean checkDeleteAnswer(User loginUser) {
        if (this.answers.size() == 0) {
            return true;
        }
        if(this.answers.stream().filter(answer -> !answer.matchWriter(this.writer)).findFirst().isPresent()) {
            return false;
        }
        if(!this.writer.equals(loginUser)) {
            return false;
        }
        return true;
    }

    public Question delete(User loginUser) {
        if (!checkDeleteAnswer(loginUser)) {
            throw new UnAuthorizedDeleteException("질문을 삭제할 수 없습니다.");
        }

        this.deleted = true;
        for (Answer answer : answers) {
            answer.delete();
        }
        return this;
    }
}
