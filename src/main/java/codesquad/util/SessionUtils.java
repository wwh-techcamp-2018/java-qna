package codesquad.util;

import codesquad.domain.User;
import codesquad.exception.UnAuthorizedUserException;
import codesquad.exception.UnidentifiedUserException;

import javax.servlet.http.HttpSession;

public class SessionUtils {
    public static final String SESSION_USER_KEY = "sessionUser";
    public static SessionUtils instance = null;

    private SessionUtils() {
    }

    public static SessionUtils getInstance() {
        if(instance == null) {
            instance = new SessionUtils();
        }
        return instance;
    }

    public User getUserFromSession(HttpSession session) {
        return (User)session.getAttribute(SESSION_USER_KEY);
    }

    public void checkSameUser(HttpSession session, Long id) throws UnAuthorizedUserException {
        if(!getUserFromSession(session).isSameId(id)) {
            throw new UnAuthorizedUserException();
        }
    }

    public void checkSameUser(User loginUser, Long id) throws UnAuthorizedUserException {
        if(!loginUser.isSameId(id)) {
            throw new UnAuthorizedUserException();
        }
    }

    public void checkLogin(HttpSession session) throws UnidentifiedUserException {
        if (getUserFromSession(session) == null) {
            throw new UnidentifiedUserException();
        }
    }
}
