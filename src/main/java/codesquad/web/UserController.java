package codesquad.web;

import codesquad.domain.User;
import codesquad.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("")
    public String create(User user) {
        //users.add(user);
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
    public String findUser(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).get();
        model.addAttribute("user", user);
        return "/user/updateForm";
    }

    @PutMapping("")
    public String updateUser(User user, HttpServletResponse response) {
        if (user.update(userRepository)) {
            return "redirect:/users";
        }
        WebUtil.alert("비밀번호가 틀렸습니다.", response);
        return null;
    }

    //
//    @PostMapping("/users")
//    public ModelAndView create2(String userId,
//                                String password,
//                                String name,
//                                String email, Model model){
//        User user = new User(userId,password,name,email);
//        users.add(user);
//        ModelAndView mav = new ModelAndView("/user/list");
//        mav.addObject("users",users);
//        return mav;
//    }
//


    //    @PostMapping("/users")
//    public String create(String userId,
//                                String password,
//                                String name,
//                                String email, Model model){
//        User user = new User(userId,password,name,email);
//        users.add(user);
//
//        model.addAttribute("users",users);
//        return "/user/list";
//    }
}
