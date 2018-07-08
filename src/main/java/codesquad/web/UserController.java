package codesquad.web;

import codesquad.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    // 서버 메모리에 사용자정보 저장
    private List<User> users = new ArrayList();

    @PostMapping("/users")
    public String create(User user) {
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "/user/list";

    }

    @GetMapping("/users/{id}")
    public String show(@PathVariable int id, Model model) {
        model.addAttribute("user", users.get(id));
        return "/user/profile";
    }

    @GetMapping("/users/{id}/form")
    public String update (@PathVariable int id,
                        Model model) {
        model.addAttribute("user", users.get(id));
        model.addAttribute("id", id);
        return "/user/updateForm";
    }

    @PutMapping("/users/{id}/update")
    public String update(@PathVariable int id,
                         User user) {
        User savedUser = users.get(id);
        savedUser.update(user);
        return "redirect:/users";
    }
}