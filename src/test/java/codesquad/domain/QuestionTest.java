package codesquad.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class QuestionTest {

    private Question question;

    @Before
    public void setUp() throws Exception {
        question = new Question("writer","title","contents");
        question.setId(1L);
    }

    @Test
    public void updateSucceed() {
        Question questionTest = new Question("writer","title","content");
        questionTest.setId(1L);
        question.update(questionTest);
        assertEquals(question,questionTest);
    }

    @Test
    public void updateFail() {
        Question questionTest = new Question("writerTest","titleTest","contentTest");
        questionTest.setId(2L);
        question.update(questionTest);
        assertNotEquals(question,questionTest);
    }
}