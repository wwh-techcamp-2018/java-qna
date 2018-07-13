package codesquad.web.user;

import codesquad.SessionUtil;
import codesquad.domain.user.User;
import codesquad.domain.user.UserRepository;
import codesquad.dto.user.UserRegisterDto;
import codesquad.dto.user.UserUpdateDto;
import codesquad.exception.user.UserNotFoundException;
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

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    @PostMapping("")
    @Transactional
    public String create(UserRegisterDto dto) {
        userRepository.save(dto.toEntity());
        return "redirect:/users";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "/user/login";
    }

    @PostMapping("/login")
    @Transactional
    public String login(String userId, String password, HttpSession session) {
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);
        if (!user.matchPassword(password)) { throw new UserNotFoundException(); }

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
    @Transactional
    public String updateUser(@PathVariable long id, UserUpdateDto dto, HttpSession session) {
        User loginedUser = SessionUtil.getUser(session);

        if (!loginedUser.matchId(id)) {
            throw new UserNotFoundException();
        }

        User user = searchUser(loginedUser.getId());
        user.update(dto);
        userRepository.save(user);

        return "redirect:/users";
    }

    private User searchUser(long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

}
