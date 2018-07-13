package codesquad.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class QuestionTest {
    private User javajigi;
    private User sanjigi;

    @Before
    public void setUp() throws Exception {
        javajigi = new User("javajigi", "pass", "자바지기", "email@email.com");
        sanjigi = new User("sanjigi", "pass", "산지비", "email@email.com");

    }

    @Test
    public void deletable() {

        Question q = new Question(javajigi, "질문", "컨텐츠", null);
        ArrayList<Answer> answers = new ArrayList<>();
        answers.add(new Answer(javajigi, true));
        q.setAnswers(answers);
        assertFalse(q.isNotDeletable());

        q = new Question(javajigi, "질문", "컨텐츠", null);
        answers = new ArrayList<>();
        answers.add(new Answer(javajigi, false));
        q.setAnswers(answers);

        assertFalse(q.isNotDeletable());

        q = new Question(javajigi, "질문", "컨텐츠", null);
        answers = new ArrayList<>();
        answers.add(new Answer(javajigi, false));
        answers.add(new Answer(sanjigi, false));
        q.setAnswers(answers);

        assertFalse(q.isNotDeletable());
    }

    @Test
    public void notDeletable() {

        Question q1 = new Question(javajigi, "질문", "컨텐츠", null);
        ArrayList<Answer> answers = new ArrayList<>();
        answers.add(new Answer(javajigi, true));
        answers.add(new Answer(sanjigi, false));
        q1.setAnswers(answers);
        assertFalse(q1.isNotDeletable());
    }
}