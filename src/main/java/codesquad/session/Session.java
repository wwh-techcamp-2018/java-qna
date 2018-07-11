package codesquad.session;

import codesquad.domain.User;
import codesquad.exception.NotLoginException;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class Session {
    public static final String SESSION_KEY = "sessionID";

    public static User getUser(HttpSession session) {
        User user = (User) session.getAttribute(SESSION_KEY);
        if(user == null){
            throw new NotLoginException();
        }
        return user;
    }

    public static Optional<User> getNullableUser(HttpSession session) {
        return Optional.ofNullable((User) session.getAttribute(SESSION_KEY));
    }


    public static void setUser(HttpSession session, User user) {
        session.setAttribute(SESSION_KEY, user);
    }
}
