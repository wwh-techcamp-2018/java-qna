package codesquad.web;

import codesquad.domain.User;
import codesquad.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private List<User> users = new ArrayList<User>() {
        {
            add(new User("sadsd", "asdasd", "asdasd", "asdad"));
            add(new User("s222adsd", "asda222sd", "as222dasd", "asdad222"));
        }
    };

    @PostMapping("/users")
    public String create(UserDto userDto) {
        users.add(userDto.toEntity());
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "/user/list";
    }

    @GetMapping("/users/{index}")
    public String show(@PathVariable int index, Model model) {
        model.addAttribute("user", users.get(index));
        return "/user/profile";
    }

    @GetMapping("/users/{index}/form")
    public String openUpdateForm(@PathVariable int index, Model model) {
        model.addAttribute("user", users.get(index));
        model.addAttribute("index", index);
        return "/user/updateForm";
    }

    @PostMapping("/users/{index}/update")
    public String updateUser(UserDto dto, @PathVariable int index) {
        User user = users.get(index);

        if (user.equalPassword(dto.getCurrentPassword())) {
            user.update(dto);
        }

        return "redirect:/users";
    }
}
