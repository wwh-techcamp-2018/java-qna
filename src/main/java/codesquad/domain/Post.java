package codesquad.domain;

import codesquad.exception.UserNotMatchException;

import javax.persistence.*;

@MappedSuperclass
public abstract class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey)
    private User writer;

    @Lob
    private String contents;
    private boolean deleted = false;

    public Post() {
    }

    public Post(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isWriter(User user) {
        return !writer.equals(user) && !deleted;
    }

    abstract public void validWriter(User user);

    public void update(Question target) {
        isWriter(target.getWriter());
        contents = target.getContents();
    }

    public void delete(User sessionedUser) {
        validWriter(sessionedUser);
        this.deleted = true;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isId(Long id) {
        return this.id == id;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
