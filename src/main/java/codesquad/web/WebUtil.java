package codesquad.web;

import codesquad.Exception.RedirectException;
import codesquad.domain.User;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class WebUtil {
    private static boolean isAlive(HttpSession session) {
        Object maybeUser = session.getAttribute("sessionedUser");
        if (maybeUser == null) {
            return false;
        }
        return true;
    }

    public static void invalidateSession(HttpSession session) {
        if (!isAlive(session)) {
            throw new RedirectException("", "/user/login");
        }
    }

    public static User fromSession(HttpSession session) {
        return (User) session.getAttribute("sessionedUser");
    }
}
