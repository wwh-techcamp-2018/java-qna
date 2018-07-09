package codesquad.domain;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    // DB 의 CRUD 한 모든 기능 구현은 끝난거야 ! .. 끝;;

    // 이렇게 만들면 자동으로 user_id 를 where 절에 넣는 query 가 생성 됨
    User findByUserId(String userId);

    // 이런 식으로 query 를 생성 가능
    User findByUserIdAndEmail(String userId, String email);
}
