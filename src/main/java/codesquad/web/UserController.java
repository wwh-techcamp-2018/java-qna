package codesquad.web;

import codesquad.domain.*;
import codesquad.service.CustomErrorMessage;
import codesquad.service.CustomException;
import codesquad.service.UserService;
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
    UserService userService;

    @PostMapping
    public String create(User user) {
        userService.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userService.getUserList());
        return "/user/list";
    }

    @GetMapping("/{userId}")
    public String show(@PathVariable String userId, Model model) {
        model.addAttribute("user", userService.getUserByUserId(userId));
        return "/user/profile";
    }

    @GetMapping("/{userId}/form")
    public String update(@PathVariable String userId, Model model, HttpSession session) {
        User targetUser = userService.getUserByUserId(userId);
        User sessionedUser = userService.convertObjectToUser(session.getAttribute(SESSION_KEY));

        if(!sessionedUser.matchUserId(targetUser))
            throw new CustomException(CustomErrorMessage.NOT_AUTHORIZED);

        model.addAttribute("user", targetUser);
        return "/user/updateForm";
    }

    @PutMapping("/{userId}")
    public String updateUser(User user, @PathVariable String userId) {
        userService.updateUser(user, userId);
        return "redirect:/users";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userService.getUserByUserIdAndPassword(userId, password);
        session.setAttribute(SESSION_KEY, user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute(SESSION_KEY);
        return "redirect:/";
    }
}
