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
     * Constructor for Role class.
     * @param role role (as string) for constructor
     */
    public Role(String role) { this.role = role; }

    /**
     * Getter method for roleId attribute of Role object.
     * @return roleId   roleId of the role object
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * Getter method for role attribute of Role object.
     * @return role   role (as string) of the role object
     */
    public String getRole() {
        return role;
    }

    /**
     * Setter method for roleId attribute of Role object. Sets roleId with given parameter.
     * @param roleId   the roleId to set
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * Setter method for role attribute of Role object. Sets role with given parameter.
     * @param role   the role (as string) to set
     */
    public void setRole(String role) {
        this.role = role;
    }
}
