package ch.heigvd.amt.project01.services.dao;

import ch.heigvd.amt.project01.model.User;

import javax.ejb.Local;
import java.sql.SQLException;
import java.util.List;

/**
 * Interface that is used to implement a DAO service that will be in charge of doing checks and requests (SQL) to a specified
 * database.
 *
 * @author Mathieu Urstein and Sébastien Boson
 */
@Local
public interface UsersManagerLocal {
    /**
     * Method for creating a new user in the database. It is used by the client who comes on the website.
     *
     * @param lastName user's last name
     * @param firstName user's first name
     * @param userName user name of the user
     * @param password user's password
     * @param passwordConfirmation user's password confirmation
     * @throws IllegalArgumentException if the given parameters by the client are incorrect
     * @throws SQLException if an error occurred with the requests to the database
     */
    void createUser(String lastName, String firstName, String userName, String password, String passwordConfirmation)
            throws IllegalArgumentException, SQLException;
    /**
     * Method for the login of an user which comes on the website. If everything is ok, this method terminates correctly.
     *
     * @param userName user name of the user
     * @param password user's password
     * @throws IllegalArgumentException if the given parameters by the client are incorrect
     * @throws SQLException if an error occurred with the requests to the database
     */
    void loginUser(String userName, String password) throws IllegalArgumentException, SQLException;

    /**
     * Method to check if an user already exists in the database with the specified id.
     *
     * @param id id of the user that we want to check
     * @return true if the user already exists, false otherwise
     * @throws SQLException if an error occurred with the requests to the database
     */
    boolean isUserExisting(long id) throws SQLException;
    /**
     * Method that will return an User object which contains information about the specified user (id) in the database.
     *
     * @param id the specific id of an user in the database
     * @return the User object
     * @throws SQLException if an error occurred with the requests to the database
     */
    User loadUser(long id) throws SQLException;
    /**
     * Method for creating a new user in the database. It is used by the REST API.
     *
     * @param user the User object that contains all the needed information to do an insertion in the database
     * @throws IllegalArgumentException if the given parameters by the client are incorrect
     * @throws SQLException if an error occurred with the requests to the database
     */
    long saveUser(User user) throws IllegalArgumentException, SQLException;
    /**
     * Method to update information about a specific user in the database (it is a full update).
     *
     * @param id specific id of an user in the database
     * @param user the User object that contains all the needed information to do an update in the database
     * @throws IllegalArgumentException if the given parameters by the client are incorrect
     * @throws SQLException if an error occurred with the requests to the database
     */
    void updateUser(long id, User user) throws IllegalArgumentException, SQLException;
    /**
     * Method to delete an user in the database.
     *
     * @param id specific id of an user in the database
     * @throws SQLException if an error occurred with the requests to the database
     */
    void deleteUser(long id) throws SQLException;

    /**
     * Method for getting all the users in the database.
     *
     * @return a list of User object that represents all the users in the database
     * @throws SQLException if an error occurred with the requests to the database
     */
    List<User> findAllUsers() throws SQLException;
}
