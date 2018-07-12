package codesquad.repository;

import codesquad.domain.Question;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends CrudRepository<Question, Long> {

    Optional<Question> findByIdAndIsDeletedFalse(Long id);

    List<Question> findAllByIsDeletedFalse();

}
