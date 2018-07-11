package codesquad.service;

import codesquad.domain.User;
import codesquad.domain.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional // JPA의 변경감지를 위해 사용
    public void update(User loginUser, User user, Long id) {
        User savedUser = userRepository.findById(id).get();
        savedUser.update(loginUser, user);
    }

    public void create(User user) {
        userRepository.save(user);
    }

    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    public User getUserByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

}
