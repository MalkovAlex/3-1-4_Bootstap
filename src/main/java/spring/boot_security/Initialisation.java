package spring.boot_security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spring.boot_security.model.Role;
import spring.boot_security.model.User;
import spring.boot_security.service.RoleService;
import spring.boot_security.service.UserService;


import javax.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Component
public class Initialisation {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public Initialisation(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void createUser() {
        if (roleService.getAllRoles().isEmpty()) {
            Role admin = new Role("ROLE_ADMIN");
            Role user = new Role("ROLE_USER");
            roleService.addRole(admin);
            roleService.addRole(user);
            Set<Role> setRole = new HashSet<>();
            setRole.add(admin);
            setRole.add(user);
            User firstAdmin = new User("adminfirst", "adminlast", (byte) 33, "admin@mail.ru",
                    "$2a$12$EkCAD7gJYqrP9N3UiK6D.ug0XpSLB4XVSS0ChJXeCh9BnpYpysL5W", setRole);

            userService.createUser(firstAdmin);
            Set<Role> setRole2 = new HashSet<>();
            setRole2.add(user);
            User secondUser = new User("userfirst", "userlast", (byte) 21, "user@mail.ru",
                    "$2a$12$eASVZ9wt91TQVUWCsG/Ku.tpp0YDVHhAewkmt14YbDfGsB9TOc3tq", setRole2);

            userService.createUser(secondUser);
            System.out.println(
                    "Cоздан пользователь: \n" +
                            "username: admin@mail.ru \n" +
                            "password: admin \n");
            System.out.println(
                    "Cоздан пользователь: \n" +
                            "username: user@mail.ru \n" +
                            "password: user \n");
        }
    }
}
