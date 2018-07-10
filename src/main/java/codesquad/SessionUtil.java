package codesquad;

import codesquad.domain.User;
import codesquad.exception.NotFoundException;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class SessionUtil {
    public static Optional<User> getMaybeUser(HttpSession session) {
        Object value = session.getAttribute("sessionedUser");
        return Optional.ofNullable((User) value);
    }
}
