package codesquad.web.domain;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Primary Key 자동 증가
    private Long id;

    @Column(length = 30, unique = true, nullable = false)
    private String userId;
    private String password;
    private String name;
    private String email;

    public User() {
    }

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getId() {
        return id;
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

    public void update(User newUser) {
        this.name = newUser.getName();
        this.email = newUser.getEmail();
    }

    public boolean isEqualPassword(User newUser) {
        return this.password.equals(newUser.getPassword());
    }
}
