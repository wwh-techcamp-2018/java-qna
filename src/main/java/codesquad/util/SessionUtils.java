package codesquad.util;

import codesquad.domain.User;

import javax.servlet.http.HttpSession;

public class SessionUtils {
    private static final String SESSIONED_USER = "sessionedUser";

    public static User getSessionedUser(HttpSession session) {
        if(isEmpty(session)) {
            return null;
        }
        return (User) session.getAttribute(SESSIONED_USER);
    }

    private static boolean isEmpty(HttpSession session) {
        return session.getAttribute(SESSIONED_USER) == null;
    }
}
