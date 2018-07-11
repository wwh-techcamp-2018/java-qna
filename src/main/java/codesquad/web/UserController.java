package codesquad.web;

import codesquad.domain.User;
import codesquad.domain.UserRepository;
import codesquad.exception.NotFoundException;
import codesquad.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;


    public UserController() {

    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        try {
            User user = login(userId, password);
            Session.setUser(session, user);
        } catch (IllegalArgumentException e) {
            return "user/login_failed";
        }
        return "redirect:/questions";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "user/login";
    }


    private User login(String userId, String password) {
        return userRepository.findByUserId(userId).filter(u -> u.match(password)).orElseThrow(NotFoundException::new);
    }

    @GetMapping("/{id}")
    public String show(@PathVariable long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("user", user);
        return "/user/profile";
    }

    @PostMapping("")
    public String create(User user, Model model) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        return "/user/form";
    }

    @GetMapping("/modifyForm")
    public String showUpdate(HttpSession session, Model model) {
        model.addAttribute("user", Session.getUser(session));
        return "/user/modifyForm";
    }

    @PostMapping("/modifyForm")
    public String updateUser(HttpSession session, User user) {
        userRepository.save(Session.getUser(session).modify(user));
        return "redirect:/users";
    }

}
