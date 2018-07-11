package codesquad.domain.user;

import codesquad.dto.user.UserUpdateDto;
import codesquad.exception.user.UserNotFoundException;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment primary key
    private Long id;

    @Column(length = 30, unique = true, nullable = false)
    private String userId;
    @Column(length = 100, nullable = false)
    private String password;
    @Column(length = 30, nullable = false)
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

    public void update(UserUpdateDto dto) {
        if (!matchPassword(dto.getCurrentPassword())) {
            throw new UserNotFoundException();
        }

        email = dto.getEmail();
        name = dto.getName();
        password = dto.getPassword();
    }

    public boolean matchPassword(String currentPassword) {
        return password.equals(currentPassword);
    }

    public boolean matchId(long id) {
        return this.id == id;
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
