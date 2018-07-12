package codesquad.web.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;


public class QuestionTest {
    private User javajigi;
    private User sanjigi;
    private Question question1;
    private Answer answer1_1;
    private Answer answer1_2;
    private Question question2;
    private Answer answer2_1;
    private Answer answer2_2;

    @Before
    public void setUp() throws Exception {
        javajigi = new User("", "", "javajigi", "");
        sanjigi = new User("", "", "sanjigi", "");
        question1 = new Question("first", "first contents", javajigi);
        answer1_1 = new Answer(question1, "first comment", javajigi);
        answer1_2 = new Answer(question1, "second comment", sanjigi);
        question2 = new Question("second", "second contents", sanjigi);
        answer2_1 = new Answer(question2, "first comment", sanjigi);
        answer2_2 = new Answer(question2, "second comment", sanjigi);

        question1.setAnswers(Arrays.asList(answer1_1, answer1_2));
        question2.setAnswers(Arrays.asList(answer2_1, answer2_2));
    }

    @Test
    public void delete() {
        question1.delete(javajigi);
        assertThat(question1.isDeleted()).isEqualTo(false);
        question2.delete(sanjigi);
        assertThat(question2.isDeleted()).isEqualTo(true);
    }

}