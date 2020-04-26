package group10.client.model.server;

import java.util.Set;

public class User {

    private Long id;
    private String username;
    private String password;
    private String name;
    private String surname;
    private String email;
    private int active;
    private Set<Role> roles;

    /**
     * Getter method for id attribute of user.
     * @return id   id of the user
     */
    public Long getId() {
        return id;
    }

    /**
     * Getter method for password attribute of user.
     * @return password   password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Getter method for active attribute of user.
     * @return active   active of the user
     */
    public int getActive() {
        return active;
    }

    /**
     * Getter method for roles attribute of user.
     * @return roles   roles of the user
     */

    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * Getter method for username attribute of user.
     * @return username   username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter method for name attribute of user.
     * @return name   name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Getter method for surname attribute of user.
     * @return surname   surname of the user
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Getter method for email attribute of user.
     * @return email   email of the user
     */
    public String getEmail() {
        return email;
    }
}
