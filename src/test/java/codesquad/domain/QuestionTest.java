package codesquad.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class QuestionTest {

    private User user;
    private Question question;

    @Before
    public void setUp() throws Exception {
        user = new User("java", "1234", "pobi", "a@b.com");
        user.setId(1L);
        question = new Question(user, "title", "contents");
        question.setId(1L);
    }

    @Test
    public void updateSucceed() {
        User testUser = new User("java", "1234", "pobi", "a@b.com");
        testUser.setId(1L);
        Question testQuestion = new Question(testUser, "title", "contents");
        testQuestion.setId(1L);
        question.update(testQuestion);
        assertEquals(question, testQuestion);
    }

    @Test
    public void updateFail_다른질문id() {
        Question questionTest = new Question(new User(), "titleTest", "contentTest");
        questionTest.setId(-1L);
        question.update(questionTest);
        assertNotEquals(question, questionTest);
    }

    @Test
    public void updateFail_다른작성자id() {
        User testUser = new User("java", "1234", "pobi", "a@b.com");
        testUser.setId(-1L);
        Question questionTest = new Question(testUser, "titleTest", "contentTest");
        questionTest.setId(1L);
        question.update(questionTest);
        assertNotEquals(question, questionTest);
    }

    @Test
    public void matchId_동일Id() {
        Question testQuestion = new Question(new User(), "", "");
        testQuestion.setId(1L);
        assertEquals(true, question.matchId(testQuestion));
    }

    @Test
    public void matchId_다른Id() {
        Question testQuestion = new Question(new User(), "", "");
        testQuestion.setId(-1L);
        assertEquals(false, question.matchId(testQuestion));
    }

    @Test
    public void matchWriter_동일Writer() {
        assertEquals(true, question.matchWriter(1L));
    }

    @Test
    public void matchWriter_다른Writer() {
        assertEquals(false, question.matchWriter(-1L));
    }
}