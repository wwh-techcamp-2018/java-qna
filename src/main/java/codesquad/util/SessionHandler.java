package codesquad.util;

import codesquad.domain.User;
import codesquad.exception.ForbiddenException;
import codesquad.exception.UserNotFoundException;
import codesquad.exception.UserNotInSessionException;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class SessionHandler {

    public static final String USER_KEY = "sessionedId";

    public static void setSession(HttpSession session, User user) {
        session.setAttribute(USER_KEY, user.getId());
    }

    public static Long getId(HttpSession session) {
        return Optional.ofNullable((Long) session.getAttribute(USER_KEY)).orElseThrow(UserNotInSessionException::new);
    }

    public static void removeSession(HttpSession session) {
        session.removeAttribute(USER_KEY);
    }

}
