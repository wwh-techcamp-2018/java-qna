package codesquad.web;

import codesquad.domain.User;
import codesquad.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping("/users")
public class UserController {
    public static final String SESSIONED_USER = "sessionedUser";
    @Autowired
    private UserService userService;


    @PostMapping("")
    public String create(User user) {
        log.info("user : {}", user);
        userService.create(user);
        return "redirect:/users";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "/user/list";

    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "/user/profile";
    }

    @GetMapping("/{id}/form")
    public String update(@PathVariable Long id,
                         Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "/user/updateForm";
    }

    @PutMapping("/{id}/update")
    public String update(@PathVariable Long id,
                         User user, HttpSession session) {
        User loginUser = (User) session.getAttribute(SESSIONED_USER);
        if (loginUser == null) {
            return "/user/login";
        }
        userService.update(loginUser, user, id);
        return "redirect:/users";
    }

    @GetMapping("/login")
    public String login() {
        return "/user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userService.getUserByUserId(userId);
        user.login(userId, password);
        session.setAttribute(SESSIONED_USER, user);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        User loginUser = (User) session.getAttribute(SESSIONED_USER);
        if (loginUser == null) {
            return "/user/login";
        }
        session.removeAttribute(SESSIONED_USER);
        return "redirect:/";
    }
}