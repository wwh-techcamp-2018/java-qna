package codesquad.domain;

import javax.persistence.*;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String writer;
    @Column(length = 100, nullable = false)
    private String title;

    @Lob
    private String contents;

    @Column(nullable = false)
    private Integer comment;

    public Question() {
        comment = 0;
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


    public String getTitle() {
        return title;
    }


    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Integer getComment() {
        return comment;
    }

}
