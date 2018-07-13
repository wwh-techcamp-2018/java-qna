package codesquad.service;

import codesquad.domain.User;
import codesquad.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserService{

    @Autowired
    private UserRepository userRepository;


    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void checkUserId(String userId) {
        if(userId == null || userId.equals(""))
            throw new CustomException(CustomErrorMessage.INCORRECT_ACCESS);

    }

    public User getUserByUserId(String userId){
        checkUserId(userId);
        return userRepository.findByUserId(userId)
                .orElseThrow( () -> new CustomException(CustomErrorMessage.NOT_VALID_PATH));
    }
    public User getUserByUserIdAndPassword(String userId, String password){
        checkUserId(userId);
        return userRepository.findByUserId(userId)
                .filter(u -> u.matchPassword(password))
                .orElseThrow( () -> new CustomException(CustomErrorMessage.NOT_VALID_PATH));
    }

    public User convertObjectToUser(Object targetUser){
        if(targetUser == null)
            throw new CustomException(CustomErrorMessage.INCORRECT_ACCESS);

        if(!(targetUser instanceof User))
            throw new CustomException(CustomErrorMessage.INCORRECT_ACCESS);

        return (User)targetUser;
    }

    /*
        user -> 바꾸기 위한 데이터 포함 중
        userId -> 업데이트 시킬 대상 식별자
     */
    public void updateUser(User user, String userId){
        User targetUser = getUserByUserId(userId);
        targetUser.updateData(user);
        userRepository.save(targetUser);
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public Iterable<User> getUserList(){
        return userRepository.findAll();
    }



}