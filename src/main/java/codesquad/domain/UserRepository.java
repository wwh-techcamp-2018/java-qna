package codesquad.domain;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
    // 자동으로 userid를 where절로 하는 query가 만들어진다.
//    User findByUserId(String userId);
//    User findByEmail(String email);
//    User findByUserIdAndEmail(String userId,String email);
}
