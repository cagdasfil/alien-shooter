package group10.server.api;

import group10.server.service.UserService;
import group10.server.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
public class UserController {

    @Autowired
    private UserService userService;

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

    @PostMapping("/users")
    public void createUser(@Valid @RequestBody User user) {
        userService.addUser(user);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable(value = "id") Long userId){
        userService.deleteUser(userId);
    }


}
