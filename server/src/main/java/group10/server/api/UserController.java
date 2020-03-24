package group10.server.api;

import group10.server.service.UserService;
import group10.server.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
public class UserController {


    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //@PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/users")
    public List<User> getUsers(){
        return userService.getAllUsers();
    }


    @GetMapping("/users/{id}")
    public User getUsersById(@PathVariable(value = "id") Long userId) {
        return userService.getUser(userId);
    }

    @PutMapping("/users/{id}")
    public void updateUser(@RequestBody User userDetails, @PathVariable(value = "id") Long userId) {
        userService.updateUser(userId,userDetails);
    }

    @PostMapping("/sign_up")
    public void createUser(@Valid @RequestBody User user) {
        userService.addUser(user);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable(value = "id") Long userId){
        userService.deleteUser(userId);
    }


}
