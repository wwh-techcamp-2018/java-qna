package codesquad.domain;

import javax.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, unique = true, nullable = false)
    private String userId;
    @Column(length=30, nullable = false)
    private String password;
    @Column(length=30, nullable = false)
    private String name;
    @Column(length=30, nullable = false)
    private String email;

    public User() {
        
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public void updateUser(User sessionedUser, User user) {
        if(sessionUserValid(sessionedUser)) {
            throw new IllegalArgumentException();
        }

        if(!password.equals(user.password)) {
            throw new IllegalArgumentException();
        }

        setName(user.name);
        setEmail(user.email);

    }

    public boolean sessionUserValid(User sessionedUser) {
        return sessionedUser != null && !userId.equals(sessionedUser.userId);
    }

    public boolean equalsUser(User currentUser) {
        if (currentUser == null)
            return false;
        return id.equals(currentUser.id);
    }
}
