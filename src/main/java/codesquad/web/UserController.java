package codesquad.web;

import codesquad.domain.*;
import codesquad.repository.UserRepository;
import codesquad.domain.exception.CustomErrorMessage;
import codesquad.domain.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {
    static final String SESSION_KEY = "sessionedUser";

    @Autowired
    UserRepository userRepository;

    @PostMapping
    public String create(User user) {
        saveUser(user);
        return "redirect:/users";
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", getUserList());
        return "/user/list";
    }

    @GetMapping("/{userId}")
    public String show(@PathVariable String userId, Model model) {
        model.addAttribute("user", getUserByUserId(userId));
        return "/user/profile";
    }

    @GetMapping("/{userId}/form")
    public String updateForm(@PathVariable String userId, Model model, HttpSession session) {
        User targetUser = getUserByUserId(userId);
        User sessionedUser = SessionUtil.getSessionedUser(session);
        sessionedUser.matchUser(targetUser);


        model.addAttribute("user", targetUser);
        return "/user/updateForm";
    }

    @PutMapping("/{userId}")
    public String update(User user, @PathVariable String userId) {
        updateUser(user, userId);
        return "redirect:/users";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = getUserByUserIdAndPassword(userId, password);
        session.setAttribute(SESSION_KEY, user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute(SESSION_KEY);
        return "redirect:/";
    }

    public User getUserByUserId(String userId) {
        if(!ValidateUtils.validateString(userId))
            throw new CustomException(CustomErrorMessage.INCORRECT_ACCESS);
        return userRepository.findByUserId(userId)
                .orElseThrow( () -> new CustomException(CustomErrorMessage.NOT_VALID_PATH));
    }

    public User getUserByUserIdAndPassword(String userId, String password){
        User matchedUser = getUserByUserId(userId);
        if(!ValidateUtils.validateString(password) ||!matchedUser.matchPassword(password))
            throw new CustomException(CustomErrorMessage.NOT_AUTHORIZED);
        return matchedUser;
//        매치패스워드를 안에서만 불러야하나?? 나중에 다시고민
//        if(!ValidateUtils.validateString(userId) || !ValidateUtils.validateString(password))
//            throw new CustomException(CustomErrorMessage.INCORRECT_ACCESS);
//        return userRepository.findByUserId(userId)
//                .filter(u -> u.matchPassword(password))
//                .orElseThrow( () -> new CustomException(CustomErrorMessage.NOT_VALID_PATH));
    }


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
