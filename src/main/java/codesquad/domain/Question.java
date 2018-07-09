package codesquad.domain;

import codesquad.util.DateUtil;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 새 data 추가시 id 가 자동으로 증가
    private Long id;

    @Column(updatable = false, length = 30, nullable = false)
    private String writer;
    @Column(length = 100, nullable = false)
    private String title;
    @Column(nullable = false)
    private String contents;
    @Column(length = 16, nullable = false)
    private String date;

    public Question() {
        date = DateUtil.getFormattedDate(new Date(), "yyyy-MM-dd HH:mm");
    }

    public String getDate() {
        return date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
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

    public void updateQuestion(Question updateQuestion) {
        if (!isOwner(updateQuestion))
            return;
        setTitle(updateQuestion.getTitle());
        setContents(updateQuestion.getContents());
    }

    private boolean isOwner(Question question) {
        return this.writer.equals(question.writer);
    }
}
