package codesquad.domain;

import codesquad.exception.UnAuthorizedUserException;
import codesquad.exception.UnDeletableQuestionException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AnswerTest {

    private User user;

    @Before
    public void setUp() throws Exception {
        user = new User("gusdk", "1234", "권현아", "1234@naver.com");
    }

    @Test
    public void isDeletable_일치() {
        Answer answer = new Answer(user, "답변");

        assertTrue(answer.isDeletable(user));
    }

    @Test(expected = UnAuthorizedUserException.class)
    public void isDeletable_불일치() {
        User user2 = new User("alstjr", "1234", "송민석", "asdf@naver.com");
        user.setId((long) 2);
        Answer answer = new Answer(user2, "답변");
        answer.isDeletable(user);
    }
}