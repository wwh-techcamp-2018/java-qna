package codesquad.util;

import codesquad.domain.User;
import codesquad.exception.InvalidLoginException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpSession;

import static org.assertj.core.api.Assertions.assertThat;


public class SessionUtilTest {
    private MockHttpSession session;
    private User user;

    @Before
    public void setUp() throws Exception {
        session = new MockHttpSession();
        user = new User();
        user.setUserId("hi");
        user.setPassword("bye");
        user.setName("name1");
    }

    @Test
    public void initUserTest() {
        SessionUtil.setUser(session, user);
        assertThat(SessionUtil.getUser(session).get()).isEqualTo(user);
    }

    @Test
    public void getUserId() {
        SessionUtil.setUser(session, user);
        assertThat(SessionUtil.getUser(session).get().getUserId()).isEqualTo(user.getUserId());
    }

    @Test
    public void checkLoginUser() {
        SessionUtil.setUser(session, user);
        assertThat(SessionUtil.checkLoginUser(session, user)).isEqualTo(true);
    }

    @Test(expected = InvalidLoginException.class)
    public void checkLoginUserFailTest() {
        User user2 = new User();
        user2.setUserId("hi2");
        user2.setPassword("bye2");
        user2.setName("name2");
        SessionUtil.checkLoginUser(session, user2);
    }
}