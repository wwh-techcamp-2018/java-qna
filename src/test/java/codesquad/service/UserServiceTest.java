package codesquad.service;

import codesquad.domain.User;
import codesquad.domain.UserRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


public class UserServiceTest {

    UserService userService;

    @Before
    public void setUp() {
        userService = new UserService();
        userService.setUserRepository(new TestUserRepository());
    }

    class TestUserRepository implements UserRepository {

        private List<User> users;

        public TestUserRepository() {
            super();
            users = Arrays.asList(
                    new User((long) 1, "junsu", "1234", "junsu", "aa@aa"),
                    new User((long) 2, "intae", "1234", "intae", "bb@bb")
            );

        }

        @Override
        public User findByUserId(String userId) {
            throw new UnsupportedOperationException();
        }

        @Override
        public User findByUserIdAndEmail(String userId, String email) {
            throw new UnsupportedOperationException();
        }

        @Override
        public <S extends User> S save(S entity) {

            return null;
        }

        @Override
        public <S extends User> Iterable<S> saveAll(Iterable<S> entities) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Optional<User> findById(Long id) {
            return users.stream().filter(u -> u.getId() == id).findFirst();
        }

        @Override
        public boolean existsById(Long aLong) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Iterable<User> findAll() {
            return null;
        }

        @Override
        public Iterable<User> findAllById(Iterable<Long> longs) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long count() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void deleteById(Long aLong) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void delete(User entity) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void deleteAll(Iterable<? extends User> entities) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void deleteAll() {
            throw new UnsupportedOperationException();
        }
    }


    @Test
    public void findById() {
        User expectedUser = new User((long) 1, "junsu", "1234", "junsu", "aa@aa");
        assertThat(userService.findById((long) 1)).isEqualTo(expectedUser);
    }

}