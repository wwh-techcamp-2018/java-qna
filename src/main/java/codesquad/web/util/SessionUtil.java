package codesquad.web.util;

import codesquad.web.domain.User;
import codesquad.web.exception.NoLoginException;

import javax.servlet.http.HttpSession;

public class SessionUtil {
    public static final String SESSION_USER = "sessionUser";

    public static User getUser(HttpSession session) {
        User loginUser = (User)session.getAttribute(SESSION_USER);
        if (loginUser == null) {
            throw new NoLoginException("로그인 후 이용해주세요.");
        }
        return loginUser;
    }

    public static void setUser(HttpSession session, User user) {
        session.setAttribute(SESSION_USER, user);
    }

    public static void logout(HttpSession session) {
        session.removeAttribute(SESSION_USER);
    }

    public static boolean matchUser(HttpSession session, User user) {
        User loginUser = getUser(session);
        return loginUser.equals(user);
    }
}
