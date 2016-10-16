package ch.heigvd.amt.project01.services;

import ch.heigvd.amt.project01.model.User;

import javax.ejb.Local;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by sebbos on 10.10.2016.
 */
@Local
public interface UsersManagerLocal {
    void createUser(String lastName, String firstName, String userName, String password, String passwordConfirmation)
            throws IllegalArgumentException, SQLException;
    void loginUser(String userName, String password) throws IllegalArgumentException, SQLException;

    User loadUser(long id) throws IllegalArgumentException, SQLException;
    long saveUser(User user) throws IllegalArgumentException, SQLException;
    void updateUser(long id, User user) throws IllegalArgumentException, SQLException;
    void deleteUser(long id) throws IllegalArgumentException, SQLException;

    List<User> findAllUsers() throws SQLException;
}
