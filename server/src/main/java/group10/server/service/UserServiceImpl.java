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
    /* User Database */
    private final UserRepository userRepository;
    /* Role Database */
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
        /* check whether any field in registration form is null or empty  */
        if(user.getUsername() == null || user.getName() == null || user.getSurname() == null || user.getEmail() == null || user.getPassword() == null ||
                user.getUsername().isEmpty() || user.getName().isEmpty() || user.getSurname().isEmpty() || user.getEmail().isEmpty() || user.getPassword().isEmpty()
        ){
            throw new ApiException.BadRequest("You should fill all blanks in registration form.");
        }
        /* check whether given username in already exists in database or not */
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new ApiException.UserAlreadyExists("username is taken, enter different username.");
        }
        /* check whether given email in already exists in database or not */
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new ApiException.UserAlreadyExists("There exist an account with this email.");
        }

        /* set user as active user*/
        user.setActive(1);
        /* encrypt user password */
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        /* get role 1 for admin, 2 for user*/
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
        /* find the user with the given ID*/
        Optional<User> user = userRepository.findById(id);
        /* check whether given user is in database or not */
        if(user.isPresent()){
           return user.get();
        }
        else{
            /* throw UserNotFound exception if user does not exist in database*/
            throw new ApiException.UserNotFound("User does not exist with the given ID :",id);
        }
    }

    @Override
    public void updateUser(Long userId, User userDetails) {
        /* check whether the user with the given id is in database or not*/
        if(userRepository.findById(userId).isPresent()){
            User u = getUser(userId);  // find the user that will be modified by id's.
            /* modify related fields*/
            u.setEmail(userDetails.getEmail());
            u.setName(userDetails.getName());
            u.setSurname(userDetails.getSurname());
            u.setPassword(userDetails.getPassword());
        }
        else{
            /* throw UserNotFound exception if the given user is not in database*/
            throw new ApiException.UserNotFound("User does not exist with the given ID :",userId);
        }

    }

    @Override
    public void deleteUser(Long userId) {
        /* check whether the user with the given id is in database or not*/
        if(userRepository.findById(userId).isPresent()){
            /* delete user*/
            userRepository.deleteById(userId);
        }
        else{
            /* throw UserNotFound exception if the given user is not in database*/
            throw new ApiException.UserNotFound("User does not exist with the given ID :",userId);
        }

    }
}
