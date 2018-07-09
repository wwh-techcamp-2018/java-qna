package codesquad.web;

import codesquad.domain.User;
import codesquad.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{index}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id));
        return "/user/profile";
    }

    @PostMapping("")
    public String create(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }


    @GetMapping("/{id}/form")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).get());
        return "/user/updateForm";
    }

    @PutMapping("/{id}/update")
    public String update(@PathVariable Long id, User modifiedUser, String oldPassword) {
        User user = userRepository.findById(id).get();
        user.update(modifiedUser, oldPassword);
        userRepository.save(user);
        return "redirect:/users";
    }
}
