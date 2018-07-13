package codesquad.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AnswerRepository extends CrudRepository<Answer, Long> {
    Optional<Answer> findById(Long aLong);
}
