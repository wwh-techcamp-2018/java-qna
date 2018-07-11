package codesquad.domain;

import javax.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, unique = true, nullable = false)
    private String userId;

    @Column(length = 30, nullable = false)
    private String password;

    @Column(length = 30, nullable = false)
    private String name;

    @Column(length = 30, nullable = false)
    private String email;

    public User() {
    }

    public User(String userId, String password, String name, String email) {
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

    public void setId(Long id) {
        this.id = id;
    }

    public static User create(String userId, String password, String name, String email) {
        return new User(userId, password, name, email);
    }

    public User modify(User u) {
        validForModify(u);
        this.setName(u.name);
        this.setEmail(u.email);

        return this;
    }

    private void validForModify(User u) {
        if(isDiffUserId(u)) throw new IllegalArgumentException("아이디가 달라용");
        if(isUnCorrectPassword(u)) throw new IllegalArgumentException("비밀번호가 달라용");
    }

    private boolean isUnCorrectPassword(User u) {
        return !this.password.equals(u.password);
    }

    private boolean isDiffUserId(User u) {
        return !this.getUserId().equals(u.getUserId());
    }

    public boolean match(String pw) {
        return this.getPassword().equals(pw);
    }
}
