package group10.server.model;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "role")
    private String role;

    /**
     * Empty class constructor
     */
    public Role() {
    }

    /**
     * @param role role (as string) for constructor
     */
    public Role(String role) { this.role = role; }

    /**
     * @return roleId   roleId of the role object
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * @return role   role (as string) of the role object
     */
    public String getRole() {
        return role;
    }

    /**
     * @param roleId   the roleId to set
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * @param role   the role (as string) to set
     */
    public void setRole(String role) {
        this.role = role;
    }
}
