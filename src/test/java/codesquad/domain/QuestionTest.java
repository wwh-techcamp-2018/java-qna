package codesquad.domain;

import codesquad.dto.QuestionDto;
import codesquad.exception.ForbiddenException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class QuestionTest {
    private Question question;
    private User writer;
    private QuestionDto dto;

    @Before
    public void setUp() throws Exception {
        writer = new User("javajigi", "password", "자바지기", "javajigi@slipp.net");
        writer.setId(1L);
        question = new Question(writer, "title", "contents");
        dto = new QuestionDto();
        dto.setTitle("newtitle");
        dto.setContents("newContents");
    }

    @Test
    public void update() {
        question.update(writer, dto);
    }

    @Test(expected = ForbiddenException.class)
    public void updateFail() {
        question.update(new User(), dto);
    }

    @Test
    public void matchWriter() {
        assertTrue(question.matchWriter(writer));
    }

    @Test
    public void matchWrongWriter() {
        assertFalse(question.matchWriter(new User()));
    }
}