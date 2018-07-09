package codesquad.web;

import codesquad.domain.User;
import codesquad.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/users")
@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    // {id} named variable
    @GetMapping("/{index}")
    public String show(@PathVariable long index, Model model) {

        model.addAttribute("user", userRepository.findById(index).get());
        return "/user/profile";
    }

    @PostMapping("")
    public String create(User user, Model model) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/updateForm/{index}")
    public String getUserInfo(@PathVariable long index, Model model) {
        model.addAttribute("user", userRepository.findById(index).get());
        return "/user/updateForm";
    }

    @PutMapping("/{index}")
    public String updateUserInfo(@PathVariable long index, User user, Model model) {
        User original = userRepository.findById(index).get();
        if(original.getPassword().equals(user.getPassword())) {
            original.updateUser(user);
            userRepository.save(original);
        }
        return "redirect:/users";
    }

    @GetMapping("")
    public String list(User user, Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

}
