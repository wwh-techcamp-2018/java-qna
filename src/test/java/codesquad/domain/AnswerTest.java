package codesquad.domain;

import codesquad.exception.ForbiddenException;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class AnswerTest {

    private Answer answer;
    private User me;
    private User other;

    @Before
    public void setUp() throws Exception {
        me = new User(1L, "id", "password", "name", "email");
        other = new User(2L, "id", "password", "name", "email");
        answer = new Answer();
        answer.setWriter(me);
    }

    @Test
    public void deleteSucceedByUser() {
        answer.deleteByUser(me);
        assertThat(answer.isDeleted()).isTrue();
    }

    @Test(expected = ForbiddenException.class)
    public void deleteFailedByUser() {
        answer.deleteByUser(other);
    }

}