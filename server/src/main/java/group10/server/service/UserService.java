package group10.server.service;

import group10.server.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    void addUser(User user);
    User getUser(Long id );
    void updateUser(Long userId, User userDetails);
    void deleteUser(Long userId);
}
