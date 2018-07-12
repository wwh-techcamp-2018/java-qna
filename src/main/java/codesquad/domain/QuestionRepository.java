package codesquad.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends CrudRepository<Question, Long> {
    @Query("from Question where deleted = false")
    public Iterable<Optional<Question>> findAllPresent();
}
