package codesquad.web;

import codesquad.domain.User;
import codesquad.domain.UserRepository;
import codesquad.exception.GlobalExceptionHandler;
import codesquad.exception.NotFoundException;
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
        User user = userRepository.findByUserId(userId).orElseThrow(IllegalArgumentException::new);
        if (user.checkPassword(password)) {
            SessionHandler.setSession(session,user);
        }

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
        model.addAttribute("user", userRepository.findById(id).get());
        return "/user/profile";
    }

    @GetMapping("/update")
    public String update(Model model, HttpSession session) {
        User user = userRepository.findById(SessionHandler.getId(session)).get();
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "/user/updateForm";
    }

    @PutMapping("/update")
    public String update(User newUser, HttpSession session) {
        User user = userRepository.findById(SessionHandler.getId(session)).get();
        if (user.checkPassword(newUser)) {
            user.update(newUser);
            userRepository.save(user);
        }
        return "redirect:/users";
    }

    @PostMapping("/form")
    public String updateForm(String userId, Model model, HttpSession session) {
        User user = userRepository.findById(SessionHandler.getId(session)).get();
        if (user.checkUserId(userId)) {
            model.addAttribute("user", user);
            return "/user/updateForm";
        }
        return "/user/update_failed";
    }

    @GetMapping("/form")
    public String signupForm(){
        return "/user/form";
    }
}
