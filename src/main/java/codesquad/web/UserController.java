package codesquad.web;

import codesquad.Exception.RedirectException;
import codesquad.domain.*;
import codesquad.util.SessionUtil;
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

    @PostMapping("/login")
    public String login(User user, HttpSession session) {
        Optional<User> maybeUser = userRepository.findByUserId(user.getUserId());
        maybeUser.filter(u -> u.isCorrectPassword(user))
                .orElseThrow(() -> new RedirectException("회원 정보를 확인해주세요."));
        session.setAttribute("sessionedUser", maybeUser.get());
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("sessionedUser");
        return "redirect:/";
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

    @GetMapping("/{index}")
    public String show(@PathVariable Long index, Model model) {
        User user = userRepository.findById(index).get();
        model.addAttribute("user", user);
        return "/user/profile";
    }

    @GetMapping("/{id}/form")
    public String findUser(@PathVariable long id, HttpSession session, Model model) {
        SessionUtil.validateSession(session);
        User user = SessionUtil.getSessionUser(session);
        user.validateUserId(id);
        model.addAttribute("user", user);
        return "/user/updateForm";
    }

    @PutMapping("")
    public String updateUser(User user, Model model, HttpSession session) {
        User original = findUserWithId(SessionUtil.getSessionUser(session).getId(), userRepository);
        original.update(user);
        userRepository.save(original);
        return "redirect:/users";
    }

    static User findUserWithId(Long id, UserRepository userRepository) {
        Optional<User> userOptional = userRepository.findById(id);
        userOptional.orElseThrow(() -> new RedirectException("잘못된 회원입니다."));
        return userOptional.get();
    }
}
