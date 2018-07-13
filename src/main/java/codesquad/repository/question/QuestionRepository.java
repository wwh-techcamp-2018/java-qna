package codesquad.repository.question;

import codesquad.domain.question.Question;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestionRepository extends CrudRepository<Question, Long> {
    List<Question> findAllByDeletedIsFalse();
}
