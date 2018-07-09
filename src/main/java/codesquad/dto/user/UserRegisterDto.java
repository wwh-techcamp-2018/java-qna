package codesquad.dto.user;

import codesquad.domain.User;

public class UserRegisterDto extends UserDto {

    private String userId;

    public UserRegisterDto(String userId, String password, String name, String email) {
        super(name, password, email);
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public User toEntity() {
        return new User(userId, password, name, email);
    }
}
