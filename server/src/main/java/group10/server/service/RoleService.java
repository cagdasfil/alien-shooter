package group10.server.service;

import group10.server.model.Role;

import java.util.List;


public interface RoleService {
    List<Role> getAllRoles();
    void createRole(Role role);
}
