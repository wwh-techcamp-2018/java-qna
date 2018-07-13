package codesquad.web.user;

import codesquad.domain.user.User;
import codesquad.dto.user.UserDto;
import codesquad.exception.user.UnauthorizedException;
import codesquad.exception.user.UserNotFoundException;
import codesquad.repository.user.UserRepository;
import codesquad.util.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;


    @PostMapping("/login")
    @Transactional
    public String login(String userId, String password, HttpSession session) {
        User user = findUserOrThrow(userId);
        user.matchPassword(password);
        AuthenticationUtil.setUserToSession(session, user);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "/user/login";
    }

    @PostMapping("")
    @Transactional
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

    @PutMapping("/me")
    @Transactional
    public String updateUser(UserDto dto, HttpSession session) {
        User user = AuthenticationUtil.getMaybeUserFromSession(session).orElseThrow(UnauthorizedException::new);
        if (user.update(dto)) {
            userRepository.save(user);
        }
        return "redirect:/users";
    }

    @GetMapping("/me/form")
    public String openUpdateForm(Model model, HttpSession session) {
        model.addAttribute("user", AuthenticationUtil.getMaybeUserFromSession(session));
        return "/user/updateForm";
    }

    private User findUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    private User findUserOrThrow(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(UserNotFoundException::new);
    }
}
