package codesquad.domain;

import codesquad.domain.user.User;
import codesquad.dto.user.UserDto;
import codesquad.exception.user.PasswordMismatchException;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {
    private User user;

    @Before
    public void setUp() throws Exception {
        user = new User("testuser", "password", "name", "email");
    }

    @Test
    public void update() {
        UserDto dto = new UserDto(user.getUserId(),
                user.getPassword(),
                "newName",
                "newEmail");
        assertThat(user.update(dto)).isTrue();
        assertThat(user.getPassword()).isEqualTo(dto.getPassword());
        assertThat(user.getName()).isEqualTo(dto.getName());
        assertThat(user.getEmail()).isEqualTo(dto.getEmail());
    }


    @Test(expected = PasswordMismatchException.class)
    public void updateWithWrongPassword() {
        UserDto dto = new UserDto(user.getUserId(),
                "wrongPassword",
                "newName",
                "newEmail");

        user.update(dto);
    }
}