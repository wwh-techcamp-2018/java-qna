package codesquad.domain;

import codesquad.domain.question.Answer;
import codesquad.domain.question.Question;
import codesquad.domain.user.User;
import codesquad.exception.user.PermissionDeniedException;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AnswerTest {
    private User user;
    private User otherUser;
    private Answer answer;

    @Before
    public void setUp() throws Exception {
        user = UserTestUtil.getTestUser();
        otherUser = UserTestUtil.getOtherUser();
        Question question = new Question(user, "title", "content");
        answer = new Answer(question, user, "userContent");
    }

    @Test
    public void delete() {
        answer.delete(user);
        assertThat(answer.getDeleted()).isTrue();
    }


    @Test (expected = PermissionDeniedException.class)
    public void deleteFail() {
        answer.delete(otherUser);
    }

}
