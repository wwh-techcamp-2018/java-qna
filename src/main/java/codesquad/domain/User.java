package codesquad.domain;

import codesquad.dto.user.UserUpdateDto;

import javax.persistence.*;

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

    public void update(UserUpdateDto dto) {
        if (password.equals(dto.getCurrentPassword())) {
            email = dto.getEmail();
            name = dto.getName();
            password = dto.getPassword();
        }
    }
}
