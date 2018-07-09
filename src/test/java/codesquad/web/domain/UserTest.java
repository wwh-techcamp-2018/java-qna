package codesquad.web.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class UserTest {

    @Test
    public void isEqualPasswordTest() {
        User user1 = new User("user1", "1111", "user1", "user1@woowahan.com");
        User user2 = new User("user2", "1111", "user2", "user2@woowahan.com");
        User user3 = new User("user3", "2222", "user3", "user3@woowahan.com");

        assertThat(user1.isEqualPassword(user2)).isEqualTo(true);
        assertThat(user1.isEqualPassword(user3)).isEqualTo(false);
    }
}