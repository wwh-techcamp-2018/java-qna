package domain;

import codesquad.domain.Answer;
import codesquad.domain.Question;
import codesquad.domain.User;
import codesquad.exception.UnAuthorizedException;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionTest {
    private Question javajigiQuestion;
    private Question javajigiUpdatedQuestion;
    private User javajigiUser;
    private User sanjigiUser;
    private Answer javajigiAnswer;
    private Answer sanjigiAnswer;

    @Before
    public void setUp() throws Exception {
        javajigiUser = new User("javajigi", "test", "슈퍼캡", "javajigi@slipp.net");
        sanjigiUser = new User("sanjigi", "test", "슈퍼캡", "javajigi@slipp.net");
        javajigiQuestion = new Question(javajigiUser, "title", "Content");
        javajigiUpdatedQuestion = new Question(javajigiUser, "title2", "Content2");
        javajigiAnswer = new Answer(javajigiUser, javajigiQuestion);
        sanjigiAnswer = new Answer(sanjigiUser, javajigiQuestion);
    }

    @Test
    public void 업데이트_아이디일치함() {
        javajigiQuestion.update(javajigiUser, javajigiUpdatedQuestion);
        assertThat(javajigiQuestion.getTitle()).isEqualTo("title2");
        assertThat(javajigiQuestion.getContents()).isEqualTo("Content2");
    }

    @Test(expected = IllegalArgumentException.class)
    public void 업데이트_아이디일치하지않음() {

        javajigiQuestion.update(sanjigiUser, javajigiUpdatedQuestion);
    }

    @Test
    public void 질문삭제_아이디일치함() {
        javajigiQuestion.delete(javajigiUser);
        assertThat(javajigiQuestion.getDeleted()).isEqualTo(true);
    }

    @Test(expected = UnAuthorizedException.class)
    public void 질문삭제_아이디일치하지않음() {
        javajigiQuestion.delete(sanjigiUser);
    }

    @Test
    public void 자신댓글_포함_질문삭제() {
        javajigiQuestion.createAnswer(javajigiUser, javajigiAnswer);
        javajigiQuestion.delete(javajigiUser);
        assertThat(javajigiQuestion.getDeleted()).isTrue();
    }

    @Test (expected = IllegalArgumentException.class)
    public void 타인댓글_포함_질문삭제() {
        javajigiQuestion.createAnswer(sanjigiUser, sanjigiAnswer);
        javajigiQuestion.delete(javajigiUser);
    }
}
