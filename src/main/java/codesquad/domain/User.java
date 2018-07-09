package codesquad.domain;

import javax.persistence.*;
import java.util.Objects;

// DTO 라고 볼 수 있다.
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 새 data 추가시 id 가 자동으로 증가
    private Long id;

    @Column(length = 30, unique = true, nullable = false)   // not null 강제하기 + 중복 없게 + length 30 이하
    private String userId;
    @Column(length = 10, nullable = false)
    private String password;
    @Column(length = 10, nullable = false)
    private String name;
    private String email;


    // Spring framework 에서는 default constructor 가 필요하다.
    // java bean 의 convention
    public User() {

    }

    public User(Long id, String userId, String password, String name, String email) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    // Spring framework 에서 내부적으로 setter method 에 접근
    // java bean 의 convention 이다.
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // template engine 에서 내부적으로 getter method 에서 접근
    // java bean convention!!
    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isSameId(String id) {
        return id.equals(userId);
    }

    private boolean isSamePassword(User user) {
        return password.equals(user.password);
    }

    public void updateUser(User updated) {
        if (!isSamePassword(updated))
            return;
        this.name = updated.name;
        this.password = updated.password;
        this.email = updated.email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId()) &&
                Objects.equals(getUserId(), user.getUserId()) &&
                Objects.equals(getPassword(), user.getPassword()) &&
                Objects.equals(getName(), user.getName()) &&
                Objects.equals(getEmail(), user.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUserId(), getPassword(), getName(), getEmail());
    }
}
