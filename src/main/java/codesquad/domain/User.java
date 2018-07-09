package codesquad.domain;

import javax.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable=false, length=30)
    private String userId;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private String email;

    public User() {
    }

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public void update(User loginUser, User user) {
        if(!loginUser.matchUserId(userId)) {
            throw new IllegalArgumentException("You can't edit your information because your id do not match.");
        }

        if(!user.matchPassword(password)) {
            throw new IllegalArgumentException("You can't edit your information because your passwords do not match.");
        }
        this.name = user.getName();
        this.email = user.getEmail();
    }

    boolean matchUserId(String userId) {
        return this.userId.equals(userId);
    }

    private boolean matchPassword(String password) {
        return this.password.equals(password);
    }

    public void login(String userId, String password) {
        if(!matchPassword(password)) {
            throw new IllegalArgumentException("Passwords do not match.");
        }
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
