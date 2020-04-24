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

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public int getActive() {
        return active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }
}
