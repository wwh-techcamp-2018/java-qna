package codesquad.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnswerRepository extends CrudRepository<Answer, Integer> {
    List<Answer> findAllByDeletedFalseAndQuestionId(int id);
}
