package codesquad.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class UserTest {
    @Test
    public void update_비밀번호_일치() {
        User user = new User("gusdk7656", "1234", "권현아", "gusdk7656@naver.com");
        User modifiedUser = new User("gusdk7656", "4321", "박주하", "juha1234@naver.com");

        assertThat(user.update(modifiedUser, "1234")).isEqualTo(modifiedUser);
    }

    @Test
    public void update_비밀번호_불일치() {
        User user = new User("gusdk7656", "1234", "권현아", "gusdk7656@naver.com");
        User modifiedUser = new User("gusdk7656", "4321", "박주하", "juha1234@naver.com");

        assertThat(user.update(modifiedUser, "1111")).isEqualTo(user);
    }
}