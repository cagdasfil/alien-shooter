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
     * Empty class constructor for User class.
     */
    public User() {

    }

    /**
     * Constructor for User class.
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
     * Getter method for id attribute of user.
     * @return id   id of the user
     */
    public Long getId() {
        return id;
    }

    /**
     * Getter method for username attribute of user.
     * @return username   username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter method for password attribute of user.
     * @return password   password of the user
     */
    public String getPassword() {
        return password;
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
     * Setter method for id attribute of user. Sets user id with given parameter.
     * @param id   the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Setter method for username attribute of user. Sets username with given parameter.
     * @param username   the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Setter method for password attribute of user. Sets password with given parameter.
     * @param password   the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Setter method for name attribute of user. Sets name with given parameter.
     * @param name   the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter method for surname attribute of user. Sets surname with given parameter.
     * @param surname   the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Setter method for email attribute of user. Sets email with given parameter.
     * @param email   the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Setter method for active attribute of user. Sets active with given parameter.
     * @param active   the active to set
     */
    public void setActive(int active) {
        this.active = active;
    }

    /**
     * Setter method for roles attribute of user. Sets roles with given parameter.
     * @param roles   the roles to set
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
