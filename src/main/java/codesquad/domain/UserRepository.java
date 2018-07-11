package codesquad.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUserId(String userId);

    User findByEmail(String Email);

    Optional<User> findByUserIdAndPassword(String userId, String password);
}
