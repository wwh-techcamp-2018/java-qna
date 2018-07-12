package codesquad.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class QuestionTest {

    private final Long SETUP_WRITER_ID = 1L;
    private final Long SETUP_QUESTION_ID = 1L;

    private User writer;
    private Question question;

    @Before
    public void setUp() throws Exception {
        writer = new User("java", "1234", "pobi", "a@b.com");
        writer.setId(SETUP_WRITER_ID);
        question = new Question(writer, "title", "contents");
        question.setId(SETUP_QUESTION_ID);
    }

    @Test
    public void updateSucceed() {
        Question testQuestion = new Question(writer, "updatedTitle", "updatedContents");
        testQuestion.setId(SETUP_QUESTION_ID);
        question.update(testQuestion);
        assertEquals(question, testQuestion);
    }

    @Test
    public void updateFail_다른질문id() {
        Question questionTest = new Question(new User(), "", "");
        questionTest.setId(-1 * SETUP_QUESTION_ID);
        question.update(questionTest);
        assertNotEquals(question, questionTest);
    }

    @Test
    public void updateFail_다른작성자id() {
        User testUser = new User();
        testUser.setId(-1 * SETUP_WRITER_ID);
        Question questionTest = new Question(testUser, "", "");
        questionTest.setId(SETUP_QUESTION_ID);
        question.update(questionTest);
        assertNotEquals(question, questionTest);
    }

    @Test
    public void matchId_동일질문Id() {
        Question testQuestion = new Question(new User(), "", "");
        testQuestion.setId(SETUP_QUESTION_ID);
        assertEquals(true, question.matchId(testQuestion));
    }

    @Test
    public void matchId_다른질문Id() {
        Question testQuestion = new Question(new User(), "", "");
        testQuestion.setId(-1 * SETUP_QUESTION_ID);
        assertEquals(false, question.matchId(testQuestion));
    }

    @Test
    public void matchWriter_동일작성자Id() {
        assertEquals(true, question.matchWriter(SETUP_WRITER_ID));
    }

    @Test
    public void matchWriter_다른작성자Id() {
        assertEquals(false, question.matchWriter(-1 * SETUP_WRITER_ID));
    }

    @Test
    public void hasAnswerFalse() {
        question.setAnswers(new ArrayList<>());
        assertEquals(false, question.hasAnswer());
    }

    @Test
    public void hasAnswerTrue() {
        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer());
        question.setAnswers(answers);
        assertEquals(true, question.hasAnswer());
    }

    @Test
    public void matchQuestionWriterAndAllAnswerWriter_성공() {
        User testUser = new User();
        testUser.setId(SETUP_WRITER_ID);
        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer(null, testUser, null));
        question.setAnswers(answers);
        assertEquals(true, question.matchQuestionWriterAndAllAnswerWriter());
    }

    @Test
    public void matchQuestionWriterAndAllAnswerWriter_실패() {
        User testUser = new User();
        testUser.setId(-1 * SETUP_WRITER_ID);
        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer(null, testUser, null));
        question.setAnswers(answers);
        assertEquals(false, question.matchQuestionWriterAndAllAnswerWriter());
    }

    @Test
    public void canDelete_성공_answer_없는경우() {
        question.setAnswers(new ArrayList<>());
        assertEquals(true, question.canDelete(SETUP_WRITER_ID));
    }

    @Test
    public void canDelete_성공_모든_답변작성자가_질문작성자와_동일() {
        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer(null, writer, null));
        question.setAnswers(answers);
        assertEquals(true, question.canDelete(SETUP_WRITER_ID));
    }

    @Test
    public void canDelete_실패_로그인사용자와_질문작성자의_id가_다름() {
        Long testUserId = -1 * SETUP_WRITER_ID;
        assertEquals(false, question.canDelete(testUserId));
    }

    @Test
    public void canDelete_실패_답변작성자가_질문작성자가_아님() {
        Long questionWriterId = SETUP_WRITER_ID;
        Long answerWriterId = -1 * SETUP_WRITER_ID;
        User answerWriter = new User();
        answerWriter.setId(answerWriterId);
        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer(null, answerWriter, null));
        question.setAnswers(answers);
        assertEquals(false, question.canDelete(questionWriterId));
    }

    @Test
    public void delete() {
        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer(question, writer, ""));
        answers.add(new Answer(question, writer, ""));
        question.setAnswers(answers);

        question.delete(SETUP_WRITER_ID);

        boolean isDeletedAllAnswer = false;
        List<Answer> actualAnswers = question.getAnswers();
        long countAnswersDeleted = actualAnswers.stream()
                                    .filter(answer -> answer.isDeleted())
                                    .count();
        if (actualAnswers.size() == countAnswersDeleted) {
            isDeletedAllAnswer = true;
        }

        assertEquals(true, (question.isDeleted() && isDeletedAllAnswer));
    }
}