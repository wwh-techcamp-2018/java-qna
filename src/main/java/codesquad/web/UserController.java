package codesquad.web;

import codesquad.domain.User;
import codesquad.domain.UserRepository;
import codesquad.dto.user.UserRegisterDto;
import codesquad.dto.user.UserUpdateDto;
import codesquad.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("")
    public String create(UserRegisterDto dto) {
        userRepository.save(dto.toEntity());
        return "redirect:/users";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable long id, Model model) {
        model.addAttribute("user", searchUser(id));
        return "/user/profile";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable long id, Model model) {
        model.addAttribute("user", searchUser(id));
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable long id, UserUpdateDto dto) {
        User user = searchUser(id);
        user.update(dto);
        userRepository.save(user);
        return "redirect:/users";
    }

    private User searchUser(long id) {
        return userRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @ExceptionHandler(NotFoundException.class)
    public String handleUserNotFoundException() {
        return "redirect:/";
    }
}
