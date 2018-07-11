package codesquad.repository;

import codesquad.domain.Question;
import org.hibernate.annotations.Where;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestionRepository extends CrudRepository<Question, Long> {

}
