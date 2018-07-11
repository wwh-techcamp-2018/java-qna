package codesquad.domain;

import codesquad.repository.QuestionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@RunWith(SpringRunner.class)
public class QuestionRepositoryTest {
    @Autowired
    private QuestionRepository questionRepository;
    @Test
    public void createTest() {
        Question question = new Question();
        question.setTitle("1234");
        questionRepository.save(question);
        assertThat(questionRepository.findById(1L).get().getTitle()).isEqualTo("1234");

    }
}