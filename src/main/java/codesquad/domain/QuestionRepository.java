package codesquad.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface QuestionRepository extends CrudRepository<Question, Long> {
    Optional<Question> findById(Long aLong);
    Iterable<Question> findByDeletedFalse();
}
