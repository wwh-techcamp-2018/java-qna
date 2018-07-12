package codesquad.domain;

import codesquad.exception.ForbiddenException;
import codesquad.exception.InvalidPasswordException;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, unique = true, nullable = false)
    private String userId;

    @Column(length = 20, nullable = false)
    private String password;

    @Column(length = 10, nullable = false)
    private String name;

    @Column(length = 30, unique = true, nullable = false)
    private String email;

    public User() {

    }

    public User(Long id, String userId, String password, String name, String email) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void validatePassword(String password) {
        if (!isPasswordMatch(password)) {
            throw new InvalidPasswordException();
        }
    }

    public void update(User newUser) {
        if (!isPasswordMatch(newUser)) {
            throw new InvalidPasswordException("/users/" + newUser.getId() + "/form");
        }
        this.name = newUser.name;
        this.email = newUser.email;
    }

    public boolean isPasswordMatch(String password) {
        return this.password.equals(password);
    }

    public boolean isPasswordMatch(User user) {
        return isPasswordMatch(user.password);
    }

    public void checkId(Long id) {
        if (!this.id.equals(id))
            throw new ForbiddenException();
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
