package codesquad.domain;

import codesquad.dto.UserDto;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {
    private User user;
    private UserDto dto;

    @Before
    public void setUp() throws Exception {
        user = new User("testuser", "password", "name", "email");
        dto = new UserDto(user.getUserId(),
                "newPassword",
                user.getPassword(),
                "newName",
                "newEmail");
    }

    @Test
    public void update() {
        assertThat(user.update(dto)).isTrue();
        assertThat(user.getPassword()).isEqualTo(dto.getPassword());
        assertThat(user.getName()).isEqualTo(dto.getName());
        assertThat(user.getEmail()).isEqualTo(dto.getEmail());
    }


    @Test
    public void updateWithWrongPassword() {
        dto = new UserDto(user.getUserId(),
                "newPassword",
                "wrongPassword",
                "newName",
                "newEmail");
        assertThat(user.update(dto)).isFalse();
        assertThat(user.getPassword()).isNotEqualTo(dto.getPassword());
        assertThat(user.getName()).isNotEqualTo(dto.getName());
        assertThat(user.getEmail()).isNotEqualTo(dto.getEmail());
    }
}