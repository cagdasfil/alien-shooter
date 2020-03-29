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


/**
 * Controller class that redirects all GET,POST,UPDATE and DELETE requests to {@link RoleService} .
 */
@RestController
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * This method redirects GET request to {@link RoleService#getAllRoles()}
     * @return roles list of all roles in database.
     */
    //@PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/roles")
    public List<Role> getRoles(){
        return roleService.getAllRoles();
    }

    /**
     * This method redirects POST request to {@link RoleService#createRole(Role)}
     * @param role role object to be added in database.
     */
    //@PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/roles")
    public void createRole(@RequestBody Role role) {
        roleService.createRole(role);
    }
}
