package codesquad.web.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Primary Key 자동 증가
    private Long id;
    @Column(length = 30, unique = true, nullable = false)
    private String userId;
    @Column(nullable = false, length = 100)
    private String password;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false, length = 100)
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

    public boolean matchPassword(User newUser) {
        if (!this.password.equals(newUser.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 잘못되었습니다.");
        }
        return true;
    }

    public boolean matchPassword(String newPassword) {
        if (!this.password.equals(newPassword)) {
            throw new IllegalArgumentException("비밀번호가 잘못되었습니다.");
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(userId, user.userId) &&
                Objects.equals(password, user.password) &&
                Objects.equals(name, user.name) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userId, password, name, email);
    }
}
