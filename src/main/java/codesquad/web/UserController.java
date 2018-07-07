package codesquad.web;

import codesquad.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private List<User> users;

    public UserController() {
        this.users = new ArrayList<>();
        this.users.add(null);
    }

    @GetMapping("/users/{id}")
    public String show(@PathVariable int id, Model model) {
        model.addAttribute("user", users.get(id));
        return "/user/profile";
    }

    @PostMapping("/users")
    public String create(User user, Model model) {
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "/user/list";
    }

    @GetMapping("/users/create")
    public String createForm(Model model) {
        return "/user/form";
    }

    @GetMapping("/users/{id}/userForm")
    public String showUpdate(@PathVariable int id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("user", users.get(id));
        return "/user/updateForm";

    }

    @PostMapping("/users/{id}/userForm")
    public String updateUser(@PathVariable int id, User user) {
        this.users.set(id, user);
        return "redirect:/users";
    }

}
