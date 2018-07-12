package codesquad.domain;

import codesquad.exception.ForbiddenException;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionTest {

    private Question question;
    private User writer;
    private User commenter;

    @Before
    public void setUp() throws Exception {
        writer = new User(1L, "me", "pass", "me", "email@me.com");
        commenter = new User(2L, "other", "word", "other", "email@other.com");
        question = new Question(1L, writer, "question", "q", false);
    }

    @Test
    public void updateSucceedByUser() {
        Question updatedQuestion = new Question(1L, writer, "qqquestion", "qqq", false);
        question.updateByUser(writer, updatedQuestion);
        assertThat(question).isEqualTo(updatedQuestion);
    }

    @Test(expected = ForbiddenException.class)
    public void updateFailedByInvalidUser() {
        Question updatedQuestion = new Question(1L, writer, "qqquestion", "qqq", false);
        question.updateByUser(commenter, updatedQuestion);
    }

    @Test
    public void deleteSucceedByUserWithoutAnswers() {
        question.deleteByUser(writer, null);
        assertThat(question.isDeleted()).isTrue();
    }

    @Test
    public void deleteSucceedByUserWithSelfAnswer() {
        List<Answer> answers = Arrays.asList(
            new Answer(1L, question, writer, "1", false),
            new Answer(2L, question, writer, "1", true)
        );
        question.deleteByUser(writer, answers);
        assertThat(question.isDeleted()).isTrue();
    }

    @Test
    public void deleteSucceedByUserWithDeletedAnswer() {
        Answer comment = new Answer(1L, question, commenter, "1", true);
        question.deleteByUser(writer, Arrays.asList(comment));
        assertThat(question.isDeleted()).isTrue();
    }

    @Test(expected = ForbiddenException.class)
    public void deleteFailedByUserWithNotDeletedAnswer() {
        Answer comment = new Answer(1L, question, commenter, "1", false);
        question.deleteByUser(writer, Arrays.asList(comment));
    }

    @Test(expected = ForbiddenException.class)
    public void deleteFailedByUser() {
        question.deleteByUser(commenter, null);
    }

}