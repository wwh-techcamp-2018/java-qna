package codesquad.domain;


import org.springframework.data.repository.CrudRepository;

import javax.persistence.*;
import java.util.Optional;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, unique = true, nullable = false)
    private String userId;


    private String password;
    private String name;
    private String email;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {

        return email;
    }

    public boolean update(CrudRepository repository){
        Optional<User> userOptional = repository.findById(this.id);
        userOptional.orElseThrow(()-> new IllegalArgumentException());
        if(this.isCorrectPassword(userOptional.get().getPassword())) {
            repository.save(this);
            return true;
        }
        return false;
    }

    public boolean isCorrectPassword(String password){
        return this.password.equals(password);
    }
}
