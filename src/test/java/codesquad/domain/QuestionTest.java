package codesquad.domain;

import codesquad.exception.UnAuthorizedUserException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class QuestionTest {

    private User loginUser;
    private Question question;

    @Before
    public void setUp() throws Exception {
        loginUser = new User("gusdk", "1234", "권현아", "tech_hak@woowahan.com");
        loginUser.setId((long) 1);
        question = new Question(loginUser, "제목", "내용");
    }

    @Test
    public void 질문수정_작성자_일치() {
        Question modifiedQuestion = new Question(loginUser, "제목수정", "내용수정");

        question.update(modifiedQuestion);
        modifiedQuestion.updateTime(question.getTime());

        assertEquals(question, modifiedQuestion);
    }

    @Test
    public void 질문수정_작성자_불일치() {
        User testUser = new User("alstjr", "1234", "송민석", "tech_mss@woowahan.com");
        Question modifiedQuestion = new Question(testUser, "제목수정", "내용수정");

        question.update(modifiedQuestion);
        modifiedQuestion.updateTime(question.getTime());

        assertNotEquals(question, modifiedQuestion);
    }

    @Test
    public void 질문삭제_작성자_일치() {
        question.addAnswers(new Answer(loginUser, "답변"));
        question.delete(loginUser);
        assertTrue(question.getDeleted());
    }

    @Test(expected = UnAuthorizedUserException.class)
    public void 질문삭제_작성자_불일치() {
        User testUser = new User("alstjr", "1234", "송민석", "tech_mss@woowahan.com");
        question.delete(testUser);
    }

    @Test(expected = UnAuthorizedUserException.class)
    public void 답변삭제_작성자_불일치() {
        User testUser = new User("alstjr", "1234", "송민석", "tech_mss@woowahan.com");
        question.addAnswers(new Answer(testUser, "답변"));
        question.delete(loginUser);
    }
}