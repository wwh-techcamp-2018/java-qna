package codesquad.web;

import codesquad.domain.User;
import codesquad.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users") // 대표 URL을 요청에 매핑
public class UserController {
    @Autowired // Spring framework이 자동으로 인터페이스 - 객체 매핑을 해줍니다.
    private UserRepository userRepository;

    // index 값이 동적으로 달라진다.
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

    @GetMapping("/{userId}")
    public String show(@PathVariable String userId, Model model) {
        model.addAttribute("user", userRepository.findByUserId(userId));
        return "/user/profile";
    }

    @GetMapping("/{userId}/form")
    public String showUpdateForm(@PathVariable String userId, Model model) {
        model.addAttribute("user", userRepository.findByUserId(userId));
        return "/user/updateForm";
    }

    @PostMapping("/{userId}")
    public String update(User user) {
        User userOrigin = userRepository.findByUserId(user.getUserId());
        userOrigin.update(user);
        userRepository.save(userOrigin);
        return "redirect:/users";
    }
}
