package codesquad.web.domain;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class AnswerTest {

    private Question question;
    private Answer answer;
    private User javajigi;
    private User sanjigi;
    private Answer deletedAnswer;

    @Before
    public void setUp() throws Exception {
        javajigi = new User("", "", "javajigi", "");
        sanjigi = new User("", "", "sanjigi", "");
        question = new Question("first", "first contents", javajigi);
        answer = new Answer(question, "first comment", javajigi);
        deletedAnswer = new Answer(question, "first comment", javajigi);
        deletedAnswer.setDeleted(true);
    }

    @Test
    public void delete() {
        answer.setDeleted(false);
        assertThat(answer.delete(javajigi)).isEqualTo(true);
        answer.setDeleted(false);
        assertThat(answer.delete(sanjigi)).isEqualTo(false);
    }


    @Test(expected = IllegalArgumentException.class)
    public void deleteException() {
        deletedAnswer.delete(javajigi);
    }
}