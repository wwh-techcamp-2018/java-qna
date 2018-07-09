package codesquad.domain;

import org.springframework.data.repository.CrudRepository;

import javax.persistence.*;
import java.util.Optional;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String writer;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String title;
    private String contents;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Question() {
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

    public boolean update(CrudRepository repository){
        Optional<Question> questionOptional = repository.findById(this.id);
        questionOptional.orElseThrow(() -> new IllegalArgumentException());
        if(isCorrectPassword(questionOptional.get().getPassword())){
            repository.save(this);
            return true;
        }
        return false;
    }

    public boolean isCorrectPassword(String password){
        return this.password.equals(password);
    }
}

