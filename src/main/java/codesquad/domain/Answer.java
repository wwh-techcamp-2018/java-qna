package codesquad.domain;

import javax.persistence.*;
import java.util.Objects;

// entity design
// user <- answer = 1:*
// question <-> answer = 1:*
// user <- question = 1:*
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Question <-> Answer : 두 entity 간의 커플링이 높기 때문에
    // bi-direction 관계를 갖도록 한다.
    // (객체지향관점에서 bi-direction은 꼭 필요할 때만 설정하자,
    // 안 그러면 나중에 entity 쪼갤때 까다로워짐)
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    @Lob
    @Column(nullable = false)
    private String contents;

    @Column
    private boolean deleted = false;

    public Answer() {
    }

    public Answer(Question question, User writer, String contents) {
        this.question = question;
        this.writer = writer;
        this.contents = contents;
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

    public Question getQuestion() {
        return question;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public String getContents() {
        return contents;
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

    public boolean matchWriter(Long writerId){
        return writer.getId().equals(writerId);
    }

    public void delete(Long loginUserId) {
        if (matchWriter(loginUserId)) {
            setDeleted(true);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return deleted == answer.deleted &&
                Objects.equals(id, answer.id) &&
                Objects.equals(question, answer.question) &&
                Objects.equals(writer, answer.writer) &&
                Objects.equals(contents, answer.contents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, question, writer, contents, deleted);
    }
}
