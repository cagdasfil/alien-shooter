package group10.server.service;

import group10.server.model.Role;

import java.util.List;


/**
 * Service which handles all requests directed from {@link group10.server.api.RoleController} .
 */

public interface RoleService {
    /**
     * This method returns all roles in database.
     * @return roles list of all roles in database.
     */
    List<Role> getAllRoles();

    /**
     * This method creates a role with the given parameter.
     * @param role role object to be added in database.
     */
    void createRole(Role role);
}
