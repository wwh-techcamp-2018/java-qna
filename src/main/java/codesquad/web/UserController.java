package codesquad.web;

import codesquad.domain.User;
import codesquad.dto.UserDto;
import codesquad.exception.UserNotFoundException;
import codesquad.repository.UserRepository;
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
    public String create(UserDto dto) {
        userRepository.save(dto.toEntity());
        return "redirect:/users";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("user", findUserOrThrow(id));
        return "/user/profile";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable Long id, UserDto dto) {
        User user = findUserOrThrow(id);

        if (user.update(dto)) {
            userRepository.save(user);
        }

        return "redirect:/users";
    }

    @GetMapping("/{id}/form")
    public String openUpdateForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", findUserOrThrow(id));
        return "/user/updateForm";
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String userNotFoundRedirect() {
        return "redirect:/users";
    }

    private User findUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }
}
