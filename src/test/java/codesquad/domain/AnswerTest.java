package codesquad.domain;

import codesquad.domain.question.Answer;
import codesquad.domain.user.User;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AnswerTest {

    User writer;
    User other;
    Answer answer;

    @Before
    public void setup() {
        writer = new User("javajigi", "password", "자바지기", "javajigi@slipp.net");
        other = new User("sanjigi", "password", "산지기", "san");
        answer = new Answer(writer, "답변");
    }

    @Test
    public void 지우려는데_작성자가_아님() {
        assertThat(answer.matchWriter(other)).isFalse();
    }

    @Test
    public void 지우려는데_작성자_맞음() {
        assertThat(answer.matchWriter(writer)).isTrue();
    }
}
