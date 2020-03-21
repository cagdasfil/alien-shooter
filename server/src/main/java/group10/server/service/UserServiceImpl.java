package group10.server.service;


import group10.server.model.User;
import group10.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void addUser(User user){
        userRepository.save(user);
    }

    public User getUser(Long id ){
       Optional<User> users = userRepository.findById(id);
       return users.get();

    }

    public void updateUser(Long userId, User userDetails) {
        User u = getUser(userId);  // find the user that will be modified by id's.
        u.setEmail(userDetails.getEmail());
        u.setName(userDetails.getName());
        u.setSurname(userDetails.getSurname());
        u.setPassword(userDetails.getPassword());
        userRepository.save(u);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
