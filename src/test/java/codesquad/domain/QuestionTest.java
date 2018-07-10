package codesquad.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class QuestionTest {

    Question question;
    User user;

    @Before
    public void setup() {
        question = new Question();
        user = new User((long)1, "kookooku", "1234", "koo", "abc@abc");
        question.setWriter(user);
        question.setId((long)1);
        question.setContents("내용");
        question.setTitle("제목");
    }

    @Test
    public void isOwner() {
        User owner = new User ((long)1, "kookooku", "1234", "koo", "abc@abc");
        assertEquals(true, question.isOwner(owner));
        owner.setId((long)2);
        assertEquals(false, question.isOwner(owner));
    }

    @Test
    public void updateQuestion() {
        Question updateQuestion = new Question();
        updateQuestion.setWriter(user);
        updateQuestion.setId((long)1);
        updateQuestion.setContents("내용수정");
        updateQuestion.setTitle("제목");

        assertEquals(true, question.updateQuestion(updateQuestion, user));

    }


}