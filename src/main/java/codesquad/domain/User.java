package codesquad.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class User {
    @Id // primary키
    @GeneratedValue(strategy = GenerationType.IDENTITY) // primary키 자동 증가
    private Long Id;

    @Column(length = 30, unique = true, nullable = false)
    private String userId;
    @Column(length = 30, nullable = false)
    private String password;
    @Column(length = 30, nullable = false)
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
        Id = id;
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
        return Id;
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

    public boolean checkPassword(String oldPassword) {
        return this.password.equals(oldPassword);
    }

    public User update(User modifiedUser, String oldPassword) {
        if (!checkPassword(oldPassword)) return this;

        setPassword(modifiedUser.getPassword());
        setName(modifiedUser.getName());
        setEmail(modifiedUser.getEmail());

        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) &&
                Objects.equals(password, user.password) &&
                Objects.equals(name, user.name) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userId, password, name, email);
    }
}
