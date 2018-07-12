package codesquad.util;

import codesquad.domain.User;
import codesquad.exception.UnAuthorizedUserException;
import codesquad.exception.UnidentifiedUserException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpSession;


public class SessionUtilsTest {

    private User user;
    private MockHttpSession session;

    @Before
    public void setUp() throws Exception {
        user = new User("gusdk7656", "1234", "권현아", "gusdk7656@naver.com");
        user.setId((long) 1);
        session = new MockHttpSession();
    }

    @Test
    public void checkSameUser_일치() {
        session.setAttribute(SessionUtils.SESSION_USER_KEY, user);
        User target = new User("gusdk7656", "1234", "권현아", "gusdk7656@naver.com");
        target.setId((long) 1);
        SessionUtils.getInstance().checkSameUser(session, target.getId());
    }

    @Test(expected = UnAuthorizedUserException.class)
    public void checkSameUser_불일치() {
        session.setAttribute(SessionUtils.SESSION_USER_KEY, user);
        User target = new User("alstjr1234", "1234", "송민석", "gusdk7656@naver.com");
        target.setId((long) 2);
        SessionUtils.getInstance().checkSameUser(session, target.getId());
    }

    @Test(expected = UnidentifiedUserException.class)
    public void checkLogin() {
        SessionUtils.getInstance().checkLogin(session);
    }
}