package codesquad.util;

import codesquad.domain.User;
import codesquad.exception.InvalidLoginException;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class SessionUtil {
    public static final String USER_KEY = "sessionedUser";

    public static void setUser(HttpSession session, User user) {
        session.setAttribute(USER_KEY, user);
    }

    public static Optional<User> getUser(HttpSession session) {
        return Optional.ofNullable((User) session.getAttribute(USER_KEY));
    }

    public static String getUserId(HttpSession session) {
        return getUser(session).orElseThrow(() -> new InvalidLoginException("로그인하지 않았습니다.")).getUserId();
    }

    public static boolean checkLoginUser(HttpSession session, User user) {
        return user.matchUserId(getUser(session).orElseThrow(() -> new InvalidLoginException("로그인하지 않았습니다.")).getUserId());

    }

    public static void removeUser(HttpSession session) {
        session.removeAttribute(USER_KEY);
    }
}
