package codesquad.dto.user;

import codesquad.domain.User;

public class UserUpdateDto extends UserDto {
    private String currentPassword;

    public UserUpdateDto(String name, String password, String currentPassword, String email) {
        super(name, password, email);
        this.currentPassword = currentPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }
}
