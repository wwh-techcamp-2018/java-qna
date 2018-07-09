package codesquad.domain;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUserId(String userId);
    User findByEmail(String email);
    User findByUserIdAndEmail(String userId, String email);
}
