package group10.server.model;


import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Set;

@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name ="username", nullable = false, unique = true, updatable = false)
    private String username;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="surname", nullable = false)
    private String surname;

    @Email
    @Column(name="email", nullable = false, unique = true)
    private String email;

    @Column(name = "active")
    private int active;
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    /**
     * Empty class constructor
     */
    public User() {

    }

    /**
     * @param user   user object for copy constructor
     */
    public User(User user) {
        this.active = user.getActive();
        this.email = user.getEmail();
        this.roles = user.getRoles();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.id = user.getId();
        this.password = user.getPassword();
    }

    /**
     * @return id   id of the user
     */
    public Long getId() {
        return id;
    }

    /**
     * @return username   username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return password   password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return name   name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * @return surname   surname of the user
     */
    public String getSurname() {
        return surname;
    }

    /**
     * @return email   email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return active   active of the user
     */
    public int getActive() {
        return active;
    }

    /**
     * @return roles   roles of the user
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * @param id   the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @param username   the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @param password   the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @param name   the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param surname   the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * @param email   the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @param active   the active to set
     */
    public void setActive(int active) {
        this.active = active;
    }

    /**
     * @param roles   the roles to set
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
