package group10.server.service;


import group10.server.model.Role;
import group10.server.model.User;
import group10.server.repository.RoleRepository;
import group10.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @Override
    public void addUser(User user){

        user.setActive(1); // active user

        Optional<Role> role = roleRepository.findById((long) 2);

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
           throw new EntityNotFoundException();
       }
    }

    @Override
    public void updateUser(Long userId, User userDetails) {
        User u = getUser(userId);  // find the user that will be modified by id's.
        u.setEmail(userDetails.getEmail());
        u.setName(userDetails.getName());
        u.setSurname(userDetails.getSurname());
        u.setPassword(userDetails.getPassword());
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
