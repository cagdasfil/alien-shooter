package group10.client.model.server;

public class Role {

    private Long roleId;
    private String role;

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
}
