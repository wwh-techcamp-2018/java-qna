package codesquad.util;

import codesquad.domain.User;
import codesquad.web.UserController;

import javax.servlet.http.HttpSession;

public class SessionUtility {
    public static final String SESSION_ATTR = "sessionedUser";

    public static User getCurrentUser(HttpSession session) {
        return (User) session.getAttribute(SESSION_ATTR);
    }

    public static void killSession(HttpSession session) {
        session.removeAttribute(SESSION_ATTR);
    }

    public static void setSession(HttpSession session, User user) {
        session.setAttribute(SESSION_ATTR, user);
    }


}
