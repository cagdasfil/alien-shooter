package group10.server.api;

import group10.server.service.UserServiceImpl;
import group10.server.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping("/users")
    public List<User> getUsers(){
        return userServiceImpl.getAllUsers();
    }


    @GetMapping("/users/{id}")
    public User getUsersById(@PathVariable(value = "id") Long userId) {
        return userServiceImpl.getUser(userId);
    }

    @PutMapping("/users/{id}")
    public void updateUser(@RequestBody User userDetails, @PathVariable(value = "id") Long userId) {
        userServiceImpl.updateUser(userId,userDetails);
    }

    @PostMapping("/users")
    public void createUser(@Valid @RequestBody User user) {
        userServiceImpl.addUser(user);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable(value = "id") Long userId){
        userServiceImpl.deleteUser(userId);
    }


}
