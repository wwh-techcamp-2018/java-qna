package codesquad.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PostTest {
    @Test
    public void updateTest() {
        Post post = new Question();
        post.setContents("test1");
        post.setWriter(new User("id","pass", "name", "email"));
        Question question = new Question();
        question.setContents("test2");
        question.setWriter(new User("id","pass", "name", "email"));
        post.update(question);
        assertThat(post.getContents()).isEqualTo("test2");
    }
}