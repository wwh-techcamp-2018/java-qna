package codesquad.web;

import codesquad.domain.User;
import codesquad.exception.InvalidUserIdException;
import codesquad.exception.UserNotFoundException;
import codesquad.exception.UserNotInSessionException;
import codesquad.repository.UserRepository;
import codesquad.util.SessionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String login() {
        return "/user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userRepository.findByUserId(userId).orElseThrow(InvalidUserIdException::new);
        user.validatePassword(password);
        SessionHandler.setSession(session, user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        SessionHandler.removeSession(session);
        return "redirect:/";
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    @PostMapping
    public String create(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).orElseThrow(UserNotFoundException::new));
        return "/user/profile";
    }

    @GetMapping("/{id}/form")
    public String update(@PathVariable Long id, Model model, HttpSession session) {
        User user = getUser(session);
        user.checkId(id);
        model.addAttribute("user", user);
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(User newUser, HttpSession session) {
        User user = getUser(session);
        user.update(newUser);
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/form")
    public String signupForm() {
        return "/user/form";
    }

    private User getUser(HttpSession session) {
        Long uid = SessionHandler.getId(session);
        return userRepository.findById(uid).orElseThrow(UserNotFoundException::new);
    }

}
