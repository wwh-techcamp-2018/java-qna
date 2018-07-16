package codesquad.domain;

import codesquad.exception.QuestionFailureException;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;

public class QuestionTest {

    Question question;
    User user1, user2;
    List<Answer> answers1, answers2;

    @Before
    public void setup() {
        user1 = new User((long) 1, "kookooku", "1234", "koo", "abc@abc");
        user2 = new User((long) 2, "kookooksdfsdfu", "1234", "updateName", "dfasdbc@abc");
        question = new Question((long) 1, user1, "제목", "contents", new Date(), false);
        answers1 = Arrays.asList(
                new Answer((long) 1, user1, question, "hi", new Date(), false),
                new Answer((long) 2, user2, question, "hi", new Date(), false)
        );
        answers2 = Arrays.asList(
                new Answer((long) 1, user1, question, "hi", new Date(), false),
                new Answer((long) 2, user1, question, "hi", new Date(), false)
        );

    }

    @Test
    public void isOwner() {
        User owner = new User((long) 1, "kookooku", "1234", "koo", "abc@abc");
        assertEquals(true, question.isOwner(owner));
        owner.setId((long) 2);
        assertEquals(false, question.isOwner(owner));
    }

    @Test
    public void questionDelete() {
        question.setAnswers(answers2);
        question.delete(user1);
        assertThat(question.isDeleted()).isEqualTo(true);
    }

    @Test(expected = QuestionFailureException.class)
    public void questionDeleteUserFailure() {
        question.setAnswers(answers2);
        question.delete(user2);
    }

    @Test(expected = QuestionFailureException.class)
    public void questionDeleteAnswerFailure() {
        question.setAnswers(answers1);
        question.delete(user1);
    }

    @Test
    public void getLatestAnswer(){
        question.setAnswers(answers1);
        assertThat(answers1.get(answers1.size() - 1 )).isEqualTo(question.getLatestAnswer());
    }

    @Test(expected = QuestionFailureException.class)
    public void getLatestAnswerExceptionTest(){
        assertThat(answers1.get(answers1.size() - 1 )).isEqualTo(question.getLatestAnswer());
    }
}