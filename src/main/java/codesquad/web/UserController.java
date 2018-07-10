package codesquad.web;

import codesquad.domain.User;
import codesquad.exception.InvalidPasswordException;
import codesquad.exception.UserNotMatchException;
import codesquad.repository.UserRepository;
import codesquad.exception.UserNotFoundException;
import codesquad.exception.UserSessionNotFoundException;
import codesquad.utility.Authentication;
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

    @PostMapping
    public String create(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    @GetMapping("/form")
    public String signUpForm() {
        return "/user/form";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable long id, Model model) {
        User user = getUserById(id);
        model.addAttribute("user", user);
        return "/user/profile";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession httpSession) {
        Long sessionId = Authentication.getId(httpSession).orElseThrow(UserSessionNotFoundException::new);

        if (!id.equals(sessionId)) {
            throw new UserNotMatchException();
        }

        model.addAttribute("user", getUserById(id));
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, User formUser, HttpSession httpSession) {
        Long sessionId = Authentication.getId(httpSession).orElseThrow(UserSessionNotFoundException::new);
        User sessionUser = userRepository.findById(sessionId).orElseThrow(UserNotFoundException::new);

        if (sessionUser.matchId(id)) {
            sessionUser.update(formUser);
            userRepository.save(sessionUser);
        }

        return "redirect:/users";
    }

    @GetMapping("/login")
    public String loginForm(HttpSession httpSession) {
        Authentication.checkLoginAlready(httpSession);
        return "user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession httpSession) {
        User user = getUserByUserId(userId);
        if (!user.matchPassword(password)) {
            throw new InvalidPasswordException();
        }
        Authentication.login(httpSession, user.getId());
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        Authentication.logout(httpSession);
        return "redirect:/";
    }

    private User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    private User getUserByUserId(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);
    }

}
