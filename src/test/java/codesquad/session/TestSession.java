package codesquad.session;

import codesquad.domain.User;
import codesquad.exception.NotLoginException;
import org.junit.Test;
import org.springframework.mock.web.MockHttpSession;

import static codesquad.session.Session.SESSION_KEY;
import static junit.framework.TestCase.assertEquals;

public class TestSession {

    @Test
    public void getUser() {
        User user = new User();
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(SESSION_KEY, user);
        assertEquals(user, Session.getUser(session));
    }

    @Test (expected = NotLoginException.class)
    public void getNotUser() {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(SESSION_KEY, null);
        Session.getUser(session);
    }

    @Test
    public void setUser(){
        User user = new User();
        MockHttpSession session = new MockHttpSession();
        Session.setUser(session, user);
        assertEquals(session.getAttribute(SESSION_KEY), user);
    }
}
