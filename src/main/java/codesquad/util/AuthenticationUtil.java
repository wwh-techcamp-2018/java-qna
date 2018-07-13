package codesquad.util;

import codesquad.domain.user.User;
import codesquad.exception.user.UnauthorizedException;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class AuthenticationUtil {
    public static final String USER_KEY = "sessionedUser";


    public static User getUserFromSession(HttpSession session) {
        User user = (User) session.getAttribute(USER_KEY);
        if (user == null) {
            throw new UnauthorizedException();
        }
        return user;
    }


    public static Optional<User> getMaybeUserFromSession(HttpSession session) {
        return Optional.ofNullable((User) session.getAttribute(USER_KEY));
    }

    public static void setUserToSession(HttpSession session, User user) {
        session.setAttribute(USER_KEY, user);
    }
}
