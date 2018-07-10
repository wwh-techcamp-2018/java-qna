package codesquad.service;

import codesquad.domain.User;
import codesquad.domain.UserRepository;
import codesquad.exception.UserLoginFailException;
import codesquad.exception.UserUpdateFailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    public void update(Long id, User sessionUser, User updateUser) throws UserUpdateFailException {
        User user = userRepository.findById(id).get();

        if(!sessionUser.isSameUser(user))
            throw new UserUpdateFailException("자기 자신의 정보만 수정할 수 있습니다.");
        user.updateUser(updateUser);
        userRepository.save(user);
    }

    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId).get();
    }

    public User login(String userId, String password) throws UserLoginFailException {
        User loginUser = findByUserId(userId);
        if(!loginUser.isSamePassword(password))
            throw new UserLoginFailException("비밀번호를 확인하기 바랍니다.");
        return loginUser;

    }
}
