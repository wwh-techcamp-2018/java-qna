package codesquad.web;

import codesquad.domain.User;
import codesquad.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    public UserController() {

    }

    @GetMapping("/users/{id}")
    public String show(@PathVariable long id, Model model) {
        Optional<User> user = userRepository.findById(id);
        model.addAttribute("user", user.get());
        return "/user/profile";
    }

    @PostMapping("/users")
    public String create(User user, Model model) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    @GetMapping("/users/create")
    public String createForm(Model model) {
        return "/user/form";
    }

    @GetMapping("/users/{id}/userForm")
    public String showUpdate(@PathVariable long id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("user", userRepository.findById(id).get());
        return "/user/updateForm";
    }

    @PostMapping("/users/{id}")
    public String updateUser(@PathVariable long id, User latest) {
        userRepository.findById(id).get();
        latest.setId(id);
        userRepository.save(latest);
        return "redirect:/users";
    }

}
