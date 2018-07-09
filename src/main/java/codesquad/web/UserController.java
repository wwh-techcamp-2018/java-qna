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
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/users")
public class UserController {
    //private Map<String, User> users = new HashMap<String, User>();
    
    @Autowired
    private UserRepository userRepository;

//    @PostMapping("")
//    public String create(String userId,
//                         String password,
//                         String name,
//                         String email, Model model){
//        User user = new User(userId, password, name, email);
//        users.add(user);
//        model.addAttribute("users", users);
//        return "/user/list";
//    }

//    //Setter 메소드 만든 후 개선
//    @PostMapping("")
//    public String create(User user, Model model){
//        users.add(user);
//        model.addAttribute("users", users);
//        return "/user/list";
//    }

    // 한가지 일만 하도록 메소드 분리
    @PostMapping
    public String create(User user){
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping
    public String list(Model model){
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    @GetMapping("/{userId}")
    public String show(@PathVariable String userId, Model model) {
        model.addAttribute("user", userRepository.findByUserId(userId));
        return "/user/profile";
    }
    @GetMapping("/{userId}/form")
    public String update(@PathVariable String userId, Model model) {
        model.addAttribute("user", userRepository.findByUserId(userId));
        return "/user/updateForm";
    }
    @PostMapping("/{userId}/form")
    public String updateUser(User user) {
        User targetUser = userRepository.findByUserId(user.getUserId());
        user.setId(targetUser.getId());
        userRepository.save(user);
        return "redirect:/users";
    }

    // 예전버전에서의 사용법
//    @PostMapping("/users")
//    public ModelAndView create(String userId,
//                               String password,
//                               String name,
//                               String email){
//        User user = new User(userId, password, name, email);
//        users.add(user);
//        ModelAndView mav = new ModelAndView("user/list");
//        mav.addObject("users", users);
//        return mav;
//    }
}
