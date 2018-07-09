package codesquad.web;

import codesquad.domain.User;
import codesquad.service.UserService;
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
@RequestMapping("/users")
public class UserController {

    // spring framework 에서 만든 userRepository 에 대한 구현체를
    // 해당 변수와 연결해줌
    @Autowired
    private UserService userService;

    /**
     * /users 경로로 오는 POST request 를 처리한다.
     * @return template path 의 template 을 반환한다.
     */
//    @PostMapping("/users")
//    public String create(String userId,
//                         String password,
//                         String name,
//                         String email, Model model) {
//        User user = new User(userId, password, name, email);
//        users.add(user);
//        model.addAttribute("users", users);
//
//        // template 의 user/list.html 을 반환해준다.
//        // 다만 경로가 /user/list 로 가는 것은 아니다.
//        return "/user/list";
//    }

    /**
     * 회원가입을 한다.
     * 위의 코드는 필드 하나하나 가져온다.
     * 이를 User 객체로 가져오자
     * <p>
     * 1. User 의 default constructor 를 만들자.
     * 2. User 의 setter method 를 만들자.
     */
    @PostMapping("")
    public String create(User user) {
        userService.save(user);
        // redirect 는 spring framework 의 예약어이다.
        // 다음과 같이 하면 /users 를 요청한다.
        return "redirect:/users";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userService.findAll());
        return "/user/list";
    }

    /**
     * User 정보를 출력해준다.
     * 동적으로 달라지는 부분은 {} 를 이용해 표현한다.
     */

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "/user/profile";
    }

    @GetMapping("/{id}/form")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "/user/updateForm";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, User updated) {
        userService.update(id,updated);
        return "redirect:/users";
    }

}
