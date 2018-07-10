package codesquad.web;

import codesquad.domain.User;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class SessionUtil {
    public static final String SESSION_NAME = "sessionedUser";

    public static User getUser(HttpSession session) {
        return Optional.of((User) session.getAttribute(SESSION_NAME)).get();//user가 세션에 없으면 NullPointException 발생
    }

    public static void setUser(HttpSession session, User user) {
        session.setAttribute(SESSION_NAME, user);
    }

    public static void removeUser(HttpSession session) {
        session.removeAttribute(SESSION_NAME);
    }
}
