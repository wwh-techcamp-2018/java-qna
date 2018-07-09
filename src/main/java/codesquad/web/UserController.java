package codesquad.web;

import codesquad.web.domain.User;
import codesquad.web.domain.UserRepository;
import codesquad.web.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public String create(User user, Model model) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable long id, Model model) {
        model.addAttribute("user", Util.findUserById(id, userRepository));
        return "/user/profile";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable long id,  Model model) {
        model.addAttribute("user", Util.findUserById(id, userRepository));
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable long id, User newUser){
        User user = Util.findUserById(id, userRepository);
        if (user.isEqualPassword(newUser)) {
            user.update(newUser);
            userRepository.save(user);
        }
        return "redirect:/users";
    }
}
