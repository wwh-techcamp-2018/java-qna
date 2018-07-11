package codesquad.web;

import codesquad.domain.User;
import codesquad.exception.UnAuthorizedDeleteException;
import codesquad.repository.UserRepository;
import codesquad.exception.InvalidLoginException;
import codesquad.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new InvalidLoginException("회원가입이 안된 아이디입니다."));

        if (!user.matchPassword(password)) {
            throw new InvalidLoginException("패스워드가 다릅니다.");
        }

        SessionUtil.setUser(session, user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        SessionUtil.removeUser(session);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).get());
        return "/user/profile";
    }

    @PostMapping
    public String create(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    @GetMapping("/{id}/form")
    public String form(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).get());
        return "/user/updateForm";
    }

    @GetMapping("/updateForm")
    public String updateForm(Model model, HttpSession session) {
        model.addAttribute("user", SessionUtil.getUser(session).get());
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, User user, HttpSession session) {
        if(!SessionUtil.checkLoginUser(session, user)) {
            throw new UnAuthorizedDeleteException("다른 사용자의 정보는 수정할 수 없습니다.");
        }

        User dbUser =  userRepository.findById(id).get();
        userRepository.save(dbUser.updateUser(user));
        return "redirect:/users";
    }
}
