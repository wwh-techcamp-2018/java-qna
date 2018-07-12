package codesquad.domain;

import codesquad.exception.AnswerFailureException;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import static org.assertj.core.api.Assertions.*;

public class AnswerTest {

    Question question;
    User user1, user2;
    Answer answer1, answer2;

    @Before
    public void setup() {
        user1 = new User((long) 1, "kookooku", "1234", "koo", "abc@abc");
        user2 = new User((long) 2, "kookooksdfsdfu", "1234", "updateName", "dfasdbc@abc");
        question = new Question((long) 1, user1, "제목", "contents", new Date(), false);
        answer1 = new Answer((long) 1, user1, question, "hi", new Date(), false);
        answer2 = new Answer((long) 2, user2, question, "hi", new Date(), false);

    }

    @Test
    public void delete() {
        answer1.delete(user1);
        assertThat(answer1.isDeleted()).isEqualTo(true);
        answer2.delete(user2);
        assertThat(answer2.isDeleted()).isEqualTo(true);
    }

    @Test(expected = AnswerFailureException.class)
    public void deleteFailure() {
        answer1.delete(user2);
    }


}
