package codesquad.domain;

import codesquad.domain.question.Answer;
import codesquad.domain.question.Question;
import codesquad.domain.user.User;
import codesquad.exception.user.PermissionDeniedException;
import codesquad.exception.user.UserNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionTest {
    private User user;
    private User otherUser;
    private Question question;
    private  List<Answer> answers;

    @Before
    public void setUp() throws Exception {
        user = UserTestUtil.getTestUser();
        otherUser = UserTestUtil.getOtherUser();
        question = new Question(user, "title", "content");
        Answer userAnswer = new Answer(question, user, "userContent");
        answers = new ArrayList<>();
        answers.add(userAnswer);
    }

    @Test
    public void delete() {
        question.delete(user);
        assertThat(question.getDeleted()).isTrue();
    }


    @Test (expected = UserNotFoundException.class)
    public void deleteFail() {
        question.delete(otherUser);
    }

    @Test
    public void deleteQuestionWithAnswer() {
        question.setAnswers(answers);
        question.delete(user);
        assertThat(question.getDeleted()).isTrue();
        for (Answer answer : answers) {
            assertThat(answer.getDeleted()).isTrue();
        }
    }

    @Test (expected = PermissionDeniedException.class)
    public void deleteQuestionWithAnswerFail() {
        Answer otherUserAnswer = new Answer(question, otherUser, "otherUserContent");
        answers.add(otherUserAnswer);
        question.setAnswers(answers);
        try {
            question.delete(user);
        } catch (PermissionDeniedException e) {
            for (Answer answer : answers) {
                assertThat(answer.getDeleted()).isFalse();
            }
            throw e;
        }
    }

    @Test
    public void deleteQuestionWithDeletedAnswer() {
        Answer otherUserAnswer = new Answer(question, otherUser, "otherUserContent");
        answers.add(otherUserAnswer);
        otherUserAnswer.delete(otherUser);
        question.setAnswers(answers);
        question.delete(user);
    }
}
