package codesquad.web.util;

import codesquad.web.domain.Question;
import codesquad.web.domain.QuestionRepository;
import codesquad.web.domain.User;
import codesquad.web.domain.UserRepository;

public class Util {

    public static Question findQuestionById(long id, QuestionRepository qr){
        return qr.findById(id).get();
    }

    public static User findUserById(long id, UserRepository ur){
        return ur.findById(id).get();
    }
}
