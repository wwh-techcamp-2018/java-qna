package codesquad.utility;

import codesquad.exception.UserSessionExistAlreadyException;
import codesquad.exception.UserSessionNotFoundException;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class Authentication {

    public static final String USER_ID = "sessionId";

    public static final Long INVALID_USER_ID = -1L;

    public static void checkLoginAlready(HttpSession httpSession) {
        if (getId(httpSession).isPresent())
            throw new UserSessionExistAlreadyException();
    }

    public static void login(HttpSession httpSession, Long id) {
        httpSession.setAttribute(USER_ID, id);
    }

    public static void logout(HttpSession httpSession) {
        httpSession.removeAttribute(USER_ID);
    }

    public static Optional<Long> getId(HttpSession httpSession) {
        return Optional.ofNullable((Long) httpSession.getAttribute(USER_ID));
    }

    public static void needLogin(HttpSession httpSession) {
        getId(httpSession).orElseThrow(UserSessionNotFoundException::new);
    }
}
