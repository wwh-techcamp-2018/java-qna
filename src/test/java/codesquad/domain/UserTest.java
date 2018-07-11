package codesquad.domain;

import codesquad.domain.user.User;
import codesquad.dto.user.UserUpdateDto;
import codesquad.exception.user.UserNotFoundException;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class UserTest {

    private User user;

    @Before
    public void setUp() throws Exception {
        user = UserTestUtil.getTestUser();
    }

    @Test (expected = UserNotFoundException.class)
    public void 비밀번호가_다를_경우_업데이트가_되지_않아야_한다() {
        UserUpdateDto dto = new UserUpdateDto("name2", "password2", "wrondPassword", "email2");

        user.update(dto);
    }

    @Test
    public void 비밀번호가_일치할_경우_업데이트_되어야_한다() {
        UserUpdateDto dto = new UserUpdateDto("name2", "password2", user.getPassword(), "email2");

        user.update(dto);

        assertThat(user.getEmail()).isEqualTo("email2");
    }
}