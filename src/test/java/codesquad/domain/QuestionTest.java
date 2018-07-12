package codesquad.domain;

import codesquad.Exception.RedirectException;
import codesquad.util.SessionUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpSession;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionTest {

    private Question question;
    private User userJava;
    private User userSan;
    private  MockHttpSession mockHttpSession;

    @Before
    public void setUp() throws Exception {
        userJava = new User("javajigi", "test", "asd", "asdq");
        userSan = new User("sanjigi", "test", "asd", "asdq");
        question = new Question(userJava, "123", "test", "test");
        mockHttpSession = new MockHttpSession();
    }

    @Test (expected = RedirectException.class)
    public void 다른사람이_삭제() {
        mockHttpSession.setAttribute("sessionedUser", userSan);
        SessionUtil.validateSession(mockHttpSession);
        question.validateWriter(SessionUtil.getSessionUser(mockHttpSession));
        question.delete();
    }

    @Test
    public void 주인이_삭제_댓글_없음() {
        mockHttpSession.setAttribute("sessionedUser", userJava);
        SessionUtil.validateSession(mockHttpSession);
        question.validateWriter(SessionUtil.getSessionUser(mockHttpSession));
        question.delete();
        assertThat(question.isDeleted()).isTrue();
    }

    @Test
    public void 주인이_삭제_댓글_주인꺼() {
        Answer answer = new Answer("String contents", userJava, question);
        question.getAnswerList().add(answer);
        mockHttpSession.setAttribute("sessionedUser", userJava);
        SessionUtil.validateSession(mockHttpSession);
        question.validateWriter(SessionUtil.getSessionUser(mockHttpSession));
        question.delete();
        assertThat(question.isDeleted()).isTrue();
        assertThat(answer.isDeleted()).isTrue();
    }

    @Test(expected = RedirectException.class)
    public void 주인이_삭제_댓글_남의꺼() {
        Answer answer = new Answer("String contents", userSan, question);
        question.getAnswerList().add(answer);
        mockHttpSession.setAttribute("sessionedUser", userJava);
        SessionUtil.validateSession(mockHttpSession);
        question.validateWriter(SessionUtil.getSessionUser(mockHttpSession));
        question.delete();
        assertThat(question.isDeleted()).isTrue();
        assertThat(answer.isDeleted()).isTrue();
    }
}