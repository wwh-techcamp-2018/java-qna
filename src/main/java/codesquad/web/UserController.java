package codesquad.web;

import codesquad.SessionUtil;
import codesquad.domain.User;
import codesquad.domain.UserRepository;
import codesquad.dto.user.UserRegisterDto;
import codesquad.dto.user.UserUpdateDto;
import codesquad.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    @PostMapping("")
    public String create(UserRegisterDto dto) {
        userRepository.save(dto.toEntity());
        return "redirect:/users";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "/user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userRepository.findByUserId(userId).orElseThrow(NotFoundException::new);
        if (!user.matchPassword(password)) { throw new NotFoundException(); }

        session.setAttribute("sessionedUser", user);
        return "redirect:/";
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
    public String updateUser(@PathVariable long id, UserUpdateDto dto, HttpSession session) {
        User loginedUser = SessionUtil.getMaybeUser(session).orElseThrow(NotFoundException::new);

        if (!loginedUser.matchId(id)) {
            throw new NotFoundException();
        }

        User user = searchUser(loginedUser.getId());
        user.update(dto);
        userRepository.save(user);

        return "redirect:/users";
    }

    private User searchUser(long id) {
        return userRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @ExceptionHandler(NotFoundException.class)
    public String handleUserNotFoundException() {
        return "redirect:/error";
    }
}
