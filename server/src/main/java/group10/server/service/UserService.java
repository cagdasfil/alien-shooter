package group10.server.service;

import group10.server.exception.ApiException;
import group10.server.model.User;

import java.util.List;


/**
 * Service which handles all requests directed from {@link group10.server.api.UserController} .
 */


public interface UserService {
    /**
     * This method returns all users in userRepository.
     * @return users List of all users in userRepository.
     */

    List<User> getAllUsers();


    /**
     * This method adds user object to userRepository with given parameter user.
     * @throws ApiException.BadRequest when user not fill all the blanks or leave one of them as empty in registration form.
     * @throws ApiException.UserAlreadyExists when chosen username or email already exists in user database.
     * @param user user information
     */
    void addUser(User user);

    /**
     * This method return user object with given id
     * @param id id of user object to be returned
     * @throws ApiException.UserNotFound when the user object with the given id does not exists in database.
     * @return User returned user object with given id.
     */

    User getUser(Long id );

    /**
     * This method updates user information with
     * @param userId id of user object to be updated.
     * @param userDetails information about user which will be changed with existing information.
     * @throws ApiException.UserNotFound when the user object with the given id does not exists in database.
     */
    void updateUser(Long userId, User userDetails);

    /**
     * This method deletes user with given userId from userRepository.
     * @param userId id of user object to be deleted.
     * @throws ApiException.UserNotFound  when the user object with the given id does not exists in database.
     */
    void deleteUser(Long userId);

    User getUserByUsername(String username);
}
