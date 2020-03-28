package group10.server.service;


import group10.server.exception.ApiException;
import group10.server.model.Role;
import group10.server.model.User;
import group10.server.repository.RoleRepository;
import group10.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


/**
 * Concrete class which implements {@link UserService} interface.
 */

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @Override
    public void addUser(User user){
        if(user.getUsername() == null || user.getName() == null || user.getSurname() == null || user.getEmail() == null || user.getPassword() == null ||
                user.getUsername().isEmpty() || user.getName().isEmpty() || user.getSurname().isEmpty() || user.getEmail().isEmpty() || user.getPassword().isEmpty()
        ){
            throw new ApiException.BadRequest("You should fill all blanks in registration form.");
        }

        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new ApiException.UserAlreadyExists("username is taken, enter different username.");
        }

        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new ApiException.UserAlreadyExists("There exist an account with this email.");
        }


        user.setActive(1); // active user
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Optional<Role> role = roleRepository.findById((long) 2); // 1 for admin, 2 for user.

        if(role.isPresent()){
            Set<Role> roles = new HashSet<>();
            roles.add(role.get());
            user.setRoles(roles);
            userRepository.save(user);
        }
        else{
            throw new EntityNotFoundException();
        }
    }

    @Override
    public User getUser(Long id ){
       Optional<User> user = userRepository.findById(id);

       if(user.isPresent()){
           return user.get();
       }
       else{
           throw new ApiException.UserNotFound("User does not exist with the given ID :",id);
       }
    }

    @Override
    public void updateUser(Long userId, User userDetails) {
        if(userRepository.findById(userId).isPresent()){
            User u = getUser(userId);  // find the user that will be modified by id's.
            u.setEmail(userDetails.getEmail());
            u.setName(userDetails.getName());
            u.setSurname(userDetails.getSurname());
            u.setPassword(userDetails.getPassword());
        }
        else{
            throw new ApiException.UserNotFound("User does not exist with the given ID :",userId);
        }

    }

    @Override
    public void deleteUser(Long userId) {
        if(userRepository.findById(userId).isPresent()){
            userRepository.deleteById(userId);
        }
        else{
            throw new ApiException.UserNotFound("User does not exist with the given ID :",userId);
        }

    }
}
