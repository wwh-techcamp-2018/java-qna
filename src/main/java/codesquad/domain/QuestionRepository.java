package codesquad.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestionRepository extends CrudRepository<Question, Integer> {
    public List<Question> findAllByDeletedFalse();
}
