package codesquad.web.util;

import codesquad.web.domain.User;

import javax.servlet.http.HttpSession;

public class SessionUtil {
    public static final String SESSION_USER = "sessionUser";

    public static User getUser(HttpSession session) {
        return (User)session.getAttribute(SESSION_USER);
    }
    public static void setUser(HttpSession session, User user) {
        session.setAttribute(SESSION_USER, user);
    }

    public static void logout(HttpSession session) {
        session.removeAttribute(SESSION_USER);
    }
}
