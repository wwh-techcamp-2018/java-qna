package codesquad.util;

import codesquad.domain.User;
import codesquad.exception.UserNotInSessionException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpSession;

import static org.assertj.core.api.Assertions.assertThat;


public class SessionHandlerTest {

    private MockHttpSession session;
    private User user;

    @Before
    public void setUp() throws Exception {
        session = new MockHttpSession();
        user = new User();
        user.setId(1L);
    }

    @Test
    public void getId() {
        session.setAttribute(SessionHandler.USER_KEY, 1L);
        assertThat(SessionHandler.getId(session)).isEqualTo(1L);
    }

    @Test(expected = UserNotInSessionException.class)
    public void notFoundUserKeyInSession() {
        SessionHandler.getId(session);
    }

    @Test
    public void setSession() {
        SessionHandler.setSession(session, user);
        assertThat(SessionHandler.getId(session)).isEqualTo(1L);
    }

    @Test
    public void removeSession() {
        SessionHandler.setSession(session, user);
        SessionHandler.removeSession(session);
        assertThat(session.getAttribute(SessionHandler.USER_KEY)).isNull();
    }
}