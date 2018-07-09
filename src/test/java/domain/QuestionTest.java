package domain;

import codesquad.domain.Question;
import codesquad.domain.User;
import codesquad.exception.UnAuthorizedException;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionTest {
    private Question savedQuestion;
    private Question updatedQuestion;
    private User javajigiUser;
    private User sanjigiUser;

    @Before
    public void setUp() throws Exception {
        javajigiUser = new User("javajigi", "test", "슈퍼캡", "javajigi@slipp.net");
        sanjigiUser = new User("sanjigi", "test", "슈퍼캡", "javajigi@slipp.net");
        savedQuestion = new Question(javajigiUser, "title", "Content");
        updatedQuestion = new Question(javajigiUser, "title2", "Content2");
    }

    @Test
    public void 업데이트_아이디일치함() {
        savedQuestion.update(javajigiUser, updatedQuestion);
        assertThat(savedQuestion.getTitle()).isEqualTo("title2");
        assertThat(savedQuestion.getContents()).isEqualTo("Content2");
    }

    @Test(expected = IllegalArgumentException.class)
    public void 업데이트_아이디일치하지않음() {
        savedQuestion.update(sanjigiUser, updatedQuestion);
    }

    @Test
    public void 삭제_아이디일치함() {
        savedQuestion.delete(javajigiUser);
        assertThat(savedQuestion.getDeleted()).isEqualTo(true);
    }

    @Test(expected = UnAuthorizedException.class)
    public void 삭제_아이디일치하지않음() {
        savedQuestion.delete(sanjigiUser);
    }
}
