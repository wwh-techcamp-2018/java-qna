package codesquad.utils;

import codesquad.domain.User;
import codesquad.exception.NoSessionedUserException;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class SessionUtil {
    public static final String SESSION_NAME = "sessionedUser";

    public static User getUser(HttpSession session) {
        return Optional.ofNullable((User) session.getAttribute(SESSION_NAME)).orElseThrow(NoSessionedUserException::new);
    }

    public static void setUser(HttpSession session, User user) {
        session.setAttribute(SESSION_NAME, user);
    }

    public static void removeUser(HttpSession session) {
        session.removeAttribute(SESSION_NAME);
    }
}
