package codesquad.domain;

import codesquad.util.DateUtil;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

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

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
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

    public boolean updateQuestion(Question updateQuestion, User user) {
        if (!isOwner(user))
            return false;
        setTitle(updateQuestion.getTitle());
        setContents(updateQuestion.getContents());
        return true;
    }

    public boolean isOwner(User user) {
        if (user == null)
            return false;
        return this.writer.isSameUser(user);
    }
}
