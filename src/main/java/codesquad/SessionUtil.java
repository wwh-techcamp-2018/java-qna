package codesquad;

import codesquad.domain.user.User;
import codesquad.exception.user.UserNotFoundException;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class SessionUtil {
    private static final String USER_KEY = "sessionedUser";

    public static User getUser(HttpSession session) {
        User user = (User) session.getAttribute(USER_KEY);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }

    public static Optional<User> getMaybeUser(HttpSession session) {
        Object value = session.getAttribute(USER_KEY);
        return Optional.ofNullable((User) value);
    }

    public static void setUser(HttpSession session, User user) {
        session.setAttribute(USER_KEY, user);
    }
}
