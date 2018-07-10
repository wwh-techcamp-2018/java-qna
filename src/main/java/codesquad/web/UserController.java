package codesquad.web;

import codesquad.domain.Question;
import codesquad.domain.User;
import codesquad.domain.UserRepository;
import codesquad.util.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static codesquad.util.SessionUtils.SESSION_USER_KEY;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    private SessionUtils utils = SessionUtils.getInstance();

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    @PostMapping("")
    public String create(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @PutMapping("")
    public String update(HttpSession session, User modifiedUser, String oldPassword) {
        User user = utils.getUserFromSession(session);
        user.update(modifiedUser, oldPassword);
        userRepository.save(user);
        return "redirect:/users";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userRepository.findByUserId(userId).orElseThrow(IllegalArgumentException::new);
        if(!user.checkPassword(password)) {
            throw new IllegalArgumentException();
        }

        session.setAttribute(SESSION_USER_KEY, user);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "/user/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.setAttribute(SESSION_USER_KEY, null);
        return "redirect:/";
    }

    @GetMapping("/updateform")
    public String updateForm() {
        return "/user/updateForm";
    }

    @GetMapping("/{index}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("user", getUserByIndex(id));
        return "/user/profile";
    }

    @GetMapping("/{id}/form")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("user", getUserByIndex(id));
        return "/user/updateForm";
    }

    private User getUserByIndex(Long index) {
        return userRepository.findById(index).orElseThrow(NullPointerException::new);
    }
}
