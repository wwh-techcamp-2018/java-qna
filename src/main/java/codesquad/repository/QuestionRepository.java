package codesquad.repository;

import codesquad.domain.Question;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<Question, Long> {
    public Iterable<Question> findByDeletedFalse();
}
