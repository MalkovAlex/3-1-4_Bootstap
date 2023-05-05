package spring.boot_security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.boot_security.model.Role;
import spring.boot_security.model.User;
import spring.boot_security.service.RoleService;
import spring.boot_security.service.RoleServiceImp;
import spring.boot_security.service.UserService;
import spring.boot_security.service.UserServiceImp;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserServiceImp userService;
    private final RoleServiceImp roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserServiceImp userService, RoleServiceImp roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    // Create


    @PostMapping("new_user")
    public String addUser(@ModelAttribute("user")User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        List<String> names = user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList());
        Set<Role> roles = new HashSet<>();
        for (String name: names){
            roles.add(roleService.getRole(name));
        }
        user.setRoles(roles);
        userService.createUser(user);
        return "redirect:/admin";
    }

    // Read

    @GetMapping()
    public String index(Model model, Principal principal) {
        User user = userService.getUsername(principal.getName());
        model.addAttribute("messages", user);
        model.addAttribute("user", new User());
        model.addAttribute("users", userService.getList());
        model.addAttribute("roleUser", roleService.getAllRoles());
        return "admin";
    }

    @GetMapping("/user/{id}")
    public String readUser(Model model, @PathVariable(name = "id") long id) {
        model.addAttribute("user", userService.getUser(id));
        return "show";
    }

    // Update

    @GetMapping(value = "change/{id}")
    public String editUser (@PathVariable("id") Long id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        model.addAttribute("roleUser", roleService.getAllRoles());
        return "change";
    }


    @PostMapping(value = "/change")
    public String edit(@ModelAttribute("user") User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        List<String> names = user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList());
        Set<Role> roles = new HashSet<>();
        for (String name: names){
            roles.add(roleService.getRole(name));
        }
        user.setRoles(roles);
        userService.updateUser(user.getId(), user);
        return "redirect:/admin";
    }

    // Delete

    @GetMapping(value = "delete/{id}")
    public String delete(@PathVariable ("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
