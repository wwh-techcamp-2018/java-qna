package codesquad.domain;


import codesquad.Exception.RedirectException;
import codesquad.util.SessionUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String title;

    private String contents;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    @Where(clause = "deleted = false")
    @OrderBy("id ASC")
    private List<Answer> answerList;
    private boolean deleted;

    public Question() {
        answerList = new ArrayList<>();
        deleted = false;
    }

    public Question(User writer, String password, String title, String contents) {
        this();
        this.writer = writer;
        this.password = password;
        this.title = title;
        this.contents = contents;
    }

    public boolean isCorrectPassword(Question question) {
        return this.password.equals(question.getPassword());
    }

    public void validateWriter(User user) {
        if (!writer.equals(user)) {
            throw new RedirectException("권한이 없습니다.");
        }
    }

    public void update(Question question) {
        if (!isCorrectPassword(question)) {
            throw new RedirectException("잘못된 비밀번호입니다.");
        }
        this.title = question.title;
        this.contents = question.contents;
    }

    public void add(Answer answer) {
        answer.setQuestion(this);
        answerList.add(answer);
    }

    public Answer getAnswer(Long answerId) {
        return answerList.stream().filter(a -> a.matchId(answerId)).findFirst().orElse(null);
    }

    public Answer getAnswer(Answer answer) {
        return answerList.stream().filter(a -> a.equals(answer)).findFirst().orElse(null);
    }

    public void removeAnswer(Answer answer, User loginUser) {
        Answer maybeAnswer = getAnswer(answer);
        if (maybeAnswer == null) {
            throw new RedirectException("잘못된 접근입니다.");
        }
        maybeAnswer.delete(loginUser);
    }

    public void removeAnswer(Long answerId, User loginUser) {
        Answer maybeAnswer = getAnswer(answerId);
        if (maybeAnswer == null) {
            throw new RedirectException("잘못된 접근입니다.");
        }
        maybeAnswer.delete(loginUser);
    }

    private boolean IsDeletable() {
        return !answerList.stream().anyMatch(a -> !this.writer.equals(a.getWriter()) && !a.isDeleted());
    }

    public void delete() {
        if (!IsDeletable()) {
            throw new RedirectException("질문을 삭제할 수 없습니다.");
        }
        answerList.stream().forEach(a -> a.setDeleted(true));
        deleted = true;
    }
}

