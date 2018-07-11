package codesquad.web;

import codesquad.domain.User;
import codesquad.domain.UserRepository;
import codesquad.util.SessionUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RequestMapping("/users")
@Controller
public class UserController {


    @Autowired
    private UserRepository userRepository;

    @GetMapping("/form")
    public String getCreateForm() {
        return "/user/form";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "/user/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        SessionUtility.killSession(session);
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(User loginUser, HttpSession session) {
        Optional<User> maybeUser = userRepository.findByUserIdAndPassword(loginUser.getUserId(), loginUser.getPassword());
        if (!maybeUser.isPresent()) {
            return "redirect:/users/login";
        }
        SessionUtility.setSession(session, maybeUser.get());
        return "redirect:/";
    }


    // {id} named variable
    @GetMapping("/{index}")
    public String show(@PathVariable long index, Model model) {
        Optional<User> maybeUser = userRepository.findById(index);
        if (!maybeUser.isPresent()) {
            return "redirect:/";
        }
        model.addAttribute("user", maybeUser.get());
        return "/user/profile";
    }

    @PostMapping("")
    public String create(User user, Model model) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/updateForm/{index}")
    public String getUserInfo(@PathVariable long index, Model model) {
        Optional<User> maybeUser = userRepository.findById(index);
        if (!maybeUser.isPresent()) {
            return "redirect:/";
        }
        model.addAttribute("user", maybeUser.get());
        return "/user/updateForm";
    }

    @PutMapping("/{index}")
    public String updateUserInfo(@PathVariable long index, User user, HttpSession session, Model model) {
        User sessionedUser = SessionUtility.getCurrentUser(session);
        Optional<User> maybeUser = userRepository.findById(index);
        if (!maybeUser.isPresent()) {
            return "redirect:/";
        }
        User original = maybeUser.get();
        original.updateUser(sessionedUser, user);
        userRepository.save(original);
        return "redirect:/users";
    }

    @GetMapping("")
    public String list(User user, Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }
}
