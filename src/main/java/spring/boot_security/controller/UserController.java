package spring.boot_security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import spring.boot_security.model.User;
import spring.boot_security.service.UserServiceImp;

import java.security.Principal;

@Controller
public class UserController {
    private final UserServiceImp userService;

    public UserController(UserServiceImp userService) {
        this.userService = userService;
    }


    @GetMapping(value = "/user")
    public String userPage (Model model, Principal principal) {
        User user = userService.getUsername(principal.getName());
        model.addAttribute("messages", user);
        return "user";
    }
}
