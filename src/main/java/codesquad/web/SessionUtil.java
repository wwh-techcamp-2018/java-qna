package codesquad.web;

import codesquad.domain.User;
import codesquad.service.CustomErrorMessage;
import codesquad.service.CustomException;
import codesquad.service.CustomRedirectException;

import javax.servlet.http.HttpSession;


import static codesquad.web.UserController.SESSION_KEY;

public class SessionUtil {
    public static User getSessionedUser(HttpSession session){
        Object userObject = session.getAttribute(SESSION_KEY);
        if(invalidateSessionUser(userObject))
            throw new CustomException(CustomErrorMessage.INCORRECT_ACCESS);

        return (User)userObject;
    }

    public static void validateLogin(HttpSession session){
        Object userObject = session.getAttribute(SESSION_KEY);
        if(invalidateSessionUser(userObject))
            throw new CustomRedirectException();
    }

    public static boolean invalidateSessionUser(Object userObject){
        return (userObject == null) || (!(userObject instanceof  User));
    }
}
