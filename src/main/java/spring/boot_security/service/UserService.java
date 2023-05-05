package spring.boot_security.service;



import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import spring.boot_security.model.User;

import java.util.List;

public interface UserService{

    User getUser(Long id);

    List<User> getList();

    void updateUser(Long id, User user);

    void createUser(User user);

    void deleteUser(Long id);

    void deleteUser(User user);

    List<User> findUser(User user);
    User findByEmail (String email);

    User getUsername (String username);
}
