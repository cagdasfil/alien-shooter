package group10.server.api;

import group10.server.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import group10.server.service.RoleService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    //@PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/roles")
    public List<Role> getRoles(){
        return roleService.getAllRoles();
    }

    //@PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/roles")
    public void createRole(@RequestBody Role role) {
        roleService.createRole(role);
    }
}
