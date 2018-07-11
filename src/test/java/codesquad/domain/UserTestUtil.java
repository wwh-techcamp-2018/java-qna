package codesquad.domain;

import codesquad.domain.user.User;

public class UserTestUtil {

    public static User getTestUser() {
        User user = new User(1L, "javajigi", "password", "자바지기", "email@email");
        return user;
    }



    public static User getOtherUser() {
        User user = new User(2L, "sanjigi", "password", "산지기", "email@email");
        return user;
    }
}
