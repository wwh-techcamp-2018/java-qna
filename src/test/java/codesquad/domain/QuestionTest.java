package codesquad.domain;

import codesquad.domain.question.Answer;
import codesquad.domain.question.Question;
import codesquad.domain.user.User;
import codesquad.dto.question.QuestionDto;
import codesquad.exception.user.ForbiddenException;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class QuestionTest {
    private Question question;
    private User writer;
    private User other;
    private QuestionDto dto;

    @Before
    public void setUp() throws Exception {
        writer = new User("javajigi", "password", "자바지기", "javajigi@slipp.net");
        writer.setId(1L);
        other = new User("sanjigi", "password", "산지기", "san");
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

    @Test
    public void 글을_지우려는데_작성자가_아님() {
        assertThat(question.isDeletable(other)).isFalse();
    }

    @Test
    public void 글을_지우려는데_작성자가_아닌_사람의_답글이_있음() {
        question.addAnswer(new Answer(writer, "이건 지울 수 있다."));
        question.addAnswer(new Answer(other, "이건 못지워"));
        assertThat(question.isDeletable(writer)).isFalse();
    }

    @Test
    public void 글을_지우려는데_내_답글_밖에_없음() {
        question.addAnswer(new Answer(writer, "이건 지울 수 있다.1"));
        question.addAnswer(new Answer(writer, "이건 지울 수 있다.2"));
        assertThat(question.isDeletable(writer)).isTrue();
    }
}