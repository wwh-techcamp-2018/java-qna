package codesquad.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUserIdAndPassword(String userId, String password);

    Optional<User> findByUserId(String userId);

}
