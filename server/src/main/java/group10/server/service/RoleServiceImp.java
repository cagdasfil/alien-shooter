package group10.server.service;

import group10.server.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import group10.server.model.Role;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Concrete class which implements {@link RoleService} interface.
 */
@Service
public class RoleServiceImp implements  RoleService {

    @Autowired
    private final RoleRepository roleRepository;

    public RoleServiceImp(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void createRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
