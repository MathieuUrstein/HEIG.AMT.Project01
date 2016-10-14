package ch.heigvd.amt.project01.services;

import javax.ejb.Local;
import java.sql.SQLException;

/**
 * Created by sebbos on 10.10.2016.
 */
@Local
public interface UsersManagerLocal {
    void createUser(String lastName, String firstName, String userName, String password, String passwordConfirmation)
            throws IllegalArgumentException, SQLException;
    void loginUser(String userName, String password) throws IllegalArgumentException, SQLException;
}
