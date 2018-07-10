package codesquad.web;

import codesquad.web.domain.User;
import codesquad.web.domain.UserRepository;
import codesquad.web.util.RepositoryUtil;
import codesquad.web.util.SessionUtil;
import netscape.security.ForbiddenTargetException;
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
    public String login(String userId, String password, HttpSession session) {
        Optional<User> maybeUser = userRepository.findByUserId(userId);
        User user = maybeUser.orElseThrow(() -> new IllegalArgumentException("해당 아이디를 찾을 수 없습니다."));
        if (user.matchPassword(password))
            SessionUtil.setUser(session, user);
        return "redirect:/";
    }

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
        model.addAttribute("user", RepositoryUtil.findUserById(id, userRepository));
        return "/user/profile";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable long id, Model model, HttpSession session) {
        User user = RepositoryUtil.findUserById(id, userRepository);
        if (!user.equals(SessionUtil.getUser(session))) {
            throw new IllegalArgumentException("다른 사람의 정보는 수정할 수 없어..");
        }
        model.addAttribute("user", user);
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable long id, User newUser, HttpSession session) {
        //내 정보만 수정할 수 있도록 변경한다.
        User loginedUser = SessionUtil.getUser(session);
        User user = RepositoryUtil.findUserById(id, userRepository);
        if (!user.equals(loginedUser)) {
            throw new ForbiddenTargetException("다른 사용자의 정보를 수정할 수 없습니다.");
        }
        if (user.matchPassword(newUser)) {
            user.update(newUser);
            userRepository.save(user);
        }
        return "redirect:/users";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        SessionUtil.logout(session);
        return "redirect:/";
    }
}
