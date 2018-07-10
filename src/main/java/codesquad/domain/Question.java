package codesquad.domain;

import codesquad.dto.QuestionDto;
import codesquad.exception.ForbiddenException;
import codesquad.exception.UnauthorizedException;

import javax.persistence.*;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;
    private String title;
    private String contents;

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

    public User getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public void update(User user, QuestionDto questionDto) {
        if (!matchWriter(user)) {
            throw new ForbiddenException();
        }
        this.title = questionDto.getTitle();
        this.contents = questionDto.getContents();
    }

    public boolean matchWriter(User user) {
        return writer.equals(user);
    }
}
