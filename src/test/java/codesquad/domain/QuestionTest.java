package codesquad.domain;

import codesquad.exception.NullAnswerException;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionTest {

    @Test
    public void listNotDeleted() {
        Question question = new Question();
        question.addAnswer(new Answer(true));
        question.addAnswer(new Answer(false));
        question.addAnswer(new Answer(false));
        List<Answer> notDeletedAnswers = question.listNotDeleted();
        assertThat(notDeletedAnswers.size()).isEqualTo(2);
    }
}