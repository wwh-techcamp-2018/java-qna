package codesquad.web;

import codesquad.domain.User;
import codesquad.exception.UserLoginFailException;
import codesquad.exception.UserUpdateFailException;
import codesquad.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {
    public final static String SESSION_ID = "sessionedUser";

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login () {
        return "/user/login";
    }

    @PostMapping("/login")
    public String loginUser(String userId, String password, HttpSession session){
        try{
            session.setAttribute(SESSION_ID, userService.login(userId,password));
            return "redirect:/";
        }catch(UserLoginFailException e){
            return "/user/login_failed";
        }
    }

    @GetMapping("/logout")
    public String logoutUser(HttpSession session){
        session.removeAttribute(SESSION_ID);
        return "redirect:/";
    }

    @GetMapping("/form")
    public String userForm() {
        return "/user/form";
    }

    @PostMapping("")
    public String create(User user) {
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userService.findAll());
        return "/user/list";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "/user/profile";
    }

    @GetMapping("/{id}/form")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "/user/updateForm";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, User updated, HttpSession session) {
        try {
            User sessionUser = getSessionUser(session);
            userService.update(id,sessionUser,updated);
            return "redirect:/users";
        } catch (UserUpdateFailException e) {
            return "/user/updateForm_failed";
        }
    }

    public static User getSessionUser(HttpSession session){
        return (User) session.getAttribute(UserController.SESSION_ID);
    }

}
