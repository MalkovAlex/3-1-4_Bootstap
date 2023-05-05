package spring.boot_security.dao;

import spring.boot_security.model.Role;

import java.util.List;


public interface RoleDao {

    List<Role> getAllRoles();
    Role getRole(String userRole);
    Role getRoleById(Long id);
    void addRole(Role role);

}
