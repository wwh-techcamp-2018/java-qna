package codesquad.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/page")
public class PageController {
    @GetMapping("/user/login")
    public String pageUserLogin() {
        return "/user/login";
    }

    @GetMapping("/user/form")
    public String pageUserForm() {
        return "/user/form";
    }

    @GetMapping("/qna")
    public String pageQna() {
        return "/qna/form";
    }
}
