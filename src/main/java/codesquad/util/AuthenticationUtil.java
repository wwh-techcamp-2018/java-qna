package codesquad.util;

import codesquad.domain.User;
import codesquad.exception.UnauthorizedException;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class AuthenticationUtil {
    public static final String USER_KEY = "sessionedUser";

    public static Optional<User> getUserFromSession(HttpSession session) {
        return Optional.ofNullable((User) session.getAttribute(USER_KEY));
    }

    public static void setUserToSession(HttpSession session, User user) {
        session.setAttribute(USER_KEY, user);
    }
}
