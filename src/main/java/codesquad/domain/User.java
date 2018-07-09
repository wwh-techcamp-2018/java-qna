package codesquad.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO: 2018. 7. 9. ";"이 포함된 문자열이 데이터베이스로 넘어가면 안 됩니다. 
    @Size(min = 1)
    @Column(length = 30, unique = true, nullable = false)
    private String userId;
    @Size(min = 1)
    @Column(nullable = false)
    private String password;
    @Size(min = 1)
    @Column(nullable = false)
    private String name;
    private String email;

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public User() {

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

    public String getEmail() {
        return email;
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

    public void update(User target) {
        if (target.getPassword().equals(password)) {
            name = target.name;
            email = target.email;
        }
    }
}
