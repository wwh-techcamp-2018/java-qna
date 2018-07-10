package codesquad.util;

import codesquad.domain.User;
import javax.servlet.http.HttpSession;

public class SessionHandler {
    public static final String USER_KEY="sessionedId";

    public static void setSession(HttpSession session,User user){
        session.setAttribute(USER_KEY,user.getId());
    }
    public static Long getId(HttpSession session){
        return (Long)session.getAttribute(USER_KEY);
    }
    public static void removeSession(HttpSession session){
        session.removeAttribute(USER_KEY);
    }
}
