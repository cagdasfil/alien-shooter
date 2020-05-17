package group10.server.api;

import group10.server.model.User;
import group10.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * Controller class that redirects all GET,POST,UPDATE and DELETE requests to {@link UserService} .
 */

@RestController
public class UserController {


    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * This method redirects GET request to {@link UserService#getAllUsers()}
     * @return users List of all users in userRepository.
     */
    //@PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/users")
    public List<User> getUsers(){
        return userService.getAllUsers();
    }

    /**
     * This method redirects GET request to {@link UserService#getUser(Long)}
     * @param userId id of user
     * @return User returned user object with given id.
     */

    //@PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/users/{id}")
    public User getUsersById(@PathVariable(value = "id") Long userId) {
        return userService.getUser(userId);
    }

    /**
     * This method redirects GET request to {@link UserService#getUserByUsername(String)}
     * @param username username of user
     * @return User returned user object with given username.
     */

    //@PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/usernames/{username}")
    public User getUserByUsername(@PathVariable(value = "username") String username) {
        return userService.getUserByUsername(username);
    }

    /**
     * This method redirects UPDATE request to {@link UserService#updateUser(Long, User)}
     * @param userDetails information about user which will be changed with existing information.
     * @param userId id of user to be updated
     */

    //@PreAuthorize("hasAnyRole('USER')")
    @PutMapping("/users/{id}")
    public void updateUser(@RequestBody User userDetails, @PathVariable(value = "id") Long userId) {
        userService.updateUser(userId,userDetails);
    }

    /**
     * This method redirects POST request to {@link UserService#addUser(User)}
     * @param user user information
     */

    @PostMapping("/sign_up")
    public void createUser(@Valid @RequestBody User user) {
        userService.addUser(user);
    }

    /**
     * This method redirects DELETE request to {@link UserService#deleteUser(Long)}
     * @param userId id of user to be deleted.
     */

    //@PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable(value = "id") Long userId){
        userService.deleteUser(userId);
    }
}
