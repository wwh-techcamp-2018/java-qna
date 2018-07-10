package codesquad.domain;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class QuestionTest {
    @Test
    public void 질문수정_작성자_일치() {
        User loginUser = new User("gusdk", "1234", "권현아", "tech_hak@woowahan.com");
        Question question = new Question(loginUser, "제목", "내용");
        Question modifiedQuestion = new Question(loginUser, "제목수정", "내용수정");

        question.update(modifiedQuestion);
        modifiedQuestion.updateTime(question.getTime());

        assertEquals(question, modifiedQuestion);
    }

    @Test
    public void 질문수정_작성자_불일치() {
        User loginUser = new User("gusdk", "1234", "권현아", "tech_hak@woowahan.com");
        User testUser = new User("alstjr", "1234", "송민석", "tech_mss@woowahan.com");
        Question question = new Question(loginUser, "제목", "내용");
        Question modifiedQuestion = new Question(testUser, "제목수정", "내용수정");

        question.update(modifiedQuestion);
        modifiedQuestion.updateTime(question.getTime());

        assertNotEquals(question, modifiedQuestion);
    }

}