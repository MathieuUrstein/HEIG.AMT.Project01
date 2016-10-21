package ch.heigvd.amt.project01.services.dao;

import ch.heigvd.amt.project01.model.User;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ch.heigvd.amt.project01.util.Utility.MAX_USER_INPUT_SIZE;

/**
 * Class that implement the UsersManagerLocal's interface to handle user accounts.
 *
 * @author Mathieu Urstein and Sébastien Boson
 */
@Stateless
public class UsersManager implements UsersManagerLocal {
    @Resource(lookup = "java:/jdbc/project01")
    private DataSource dataSource;

    @Override
    public void createUser(String lastName, String firstName, String userName, String password, String passwordConfirmation)
            throws IllegalArgumentException, SQLException {
        if (userName.isEmpty() || password.isEmpty() || passwordConfirmation.isEmpty()) {
            throw new IllegalArgumentException("Username, password and password confirmation can't be empty!");
        }

        // check if user entries are not too long (> MAX_USER_INPUT_SIZE)
        checkUserEntriesSize(lastName, firstName, userName, password);

        // check if the user name is a valid email address
        if (!isValidEmailAddress(userName)) {
            throw new IllegalArgumentException("Invalid email address!");
        }

        if (!password.equals(passwordConfirmation)) {
            throw new IllegalArgumentException("Password confirmation doesn't match password!");
        }

        saveUser(new User(lastName, firstName, userName, password));
    }

    @Override
    public void loginUser(String userName, String password) throws IllegalArgumentException, SQLException {
        if (userName.isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException("Username and password can't be empty!");
        }

        if (!isValidEmailAddress(userName)) {
            throw new IllegalArgumentException("Invalid email address!");
        }

        if (!isUserExisting(userName)) {
            throw new IllegalArgumentException("The specified username doesn't exist!");
        }

        // try catch with resource to close automatically the connection with the database in all cases
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT password FROM user " +
                           "WHERE userName = ?;";

            // we use prepared statements to avoid SQL injections
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, userName);
            ResultSet rs = pstmt.executeQuery();

            rs.next();

            if (!(rs.getString("password").equals(password))) {
                throw new IllegalArgumentException("The specified password is incorrect!");
            }
        }
    }

    @Override
    public boolean isUserExisting(long id) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT COUNT(*) AS userExisting FROM user " +
                           "WHERE id = ?;";

            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            rs.next();

            if (rs.getInt("userExisting") == 1) {
                return true;
            }

            return false;
        }
    }

    @Override
    public User loadUser(long id) throws SQLException {
        User user;

        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT * FROM user " +
                           "WHERE id = ?;";

            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            rs.next();

            user = new User(id, rs.getString("lastName"), rs.getString("firstName"), rs.getString("userName"), rs.getString("password"));
        }

        return user;
    }

    @Override
    public long saveUser(User user) throws IllegalArgumentException, SQLException {
        if (isUserExisting(user.getUserName())) {
            throw new IllegalArgumentException("The specified username already exists!");
        }

        try (Connection connection = dataSource.getConnection()) {
            String query = "INSERT INTO user (lastName, firstName, userName, password) VALUES (?, ?, ?, ?);";

            PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, user.getLastName());
            pstmt.setString(2, user.getFirstName());
            pstmt.setString(3, user.getUserName());
            pstmt.setString(4, user.getPassword());

            if (pstmt.executeUpdate() == 0) {
                throw new SQLException("Creating user failed, no rows affected!");
            }

            ResultSet rs = pstmt.getGeneratedKeys();

            if (rs.next()) {
                return rs.getLong(1);
            }
            else {
                throw new SQLException("Creating user failed, no ID obtained!");
            }
        }
    }

    @Override
    public void updateUser(long id, User user) throws IllegalArgumentException, SQLException {
        // case if new user name already exits (other than himself)
        if (isOtherUserExisting(id, user.getUserName())) {
            throw new IllegalArgumentException("The specified username already exists!");
        }

        try (Connection connection = dataSource.getConnection()) {
            String query = "UPDATE user " +
                           "SET lastName = ?, firstName = ?, userName = ?, password = ? " +
                           "WHERE id = ?;";

            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, user.getLastName());
            pstmt.setString(2, user.getFirstName());
            pstmt.setString(3, user.getUserName());
            pstmt.setString(4, user.getPassword());
            pstmt.setLong(5, id);

            if (pstmt.executeUpdate() == 0) {
                throw new SQLException("Updating user failed, no rows affected!");
            }
        }
    }

    @Override
    public void deleteUser(long id) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            String query = "DELETE FROM user " +
                           "WHERE id = ?;";

            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setLong(1, id);

            if (pstmt.executeUpdate() == 0) {
                throw new SQLException("Deleting user failed, no rows affected!");
            }
        }
    }

    @Override
    public List<User> findAllUsers() throws SQLException {
        List<User> users = new ArrayList<User>();

        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT * FROM user;";

            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("id");
                String lastName = rs.getString("lastName");
                String firstName = rs.getString("firstName");
                String userName = rs.getString("userName");
                String password = rs.getString("password");

                users.add(new User(id, lastName, firstName, userName, password));
            }

        }

        return users;
    }

    /**
     * Method that is used to check the size of the user's entries.
     *
     * @param lastName user's last name
     * @param firstName user's first name
     * @param userName user name of the user
     * @param password user's password
     * @throws IllegalArgumentException if the given parameters by the client are incorrect (too long)
     */
    private void checkUserEntriesSize(String lastName, String firstName, String userName, String password) throws IllegalArgumentException {
        if (lastName.length() > MAX_USER_INPUT_SIZE) {
            throw new IllegalArgumentException("Last name is too long (max " + MAX_USER_INPUT_SIZE + " characters)!");
        }

        if (firstName.length() > MAX_USER_INPUT_SIZE) {
            throw new IllegalArgumentException("First name is too long (max " + MAX_USER_INPUT_SIZE + " characters)!");
        }

        if (userName.length() > MAX_USER_INPUT_SIZE) {
            throw new IllegalArgumentException("User name is too long (max " + MAX_USER_INPUT_SIZE + " characters)!");
        }

        if (password.length() > MAX_USER_INPUT_SIZE) {
            throw new IllegalArgumentException("Password is too long (max " + MAX_USER_INPUT_SIZE + " characters)!");
        }
    }

    /**
     * Method used to check if the specified email is valid or not.
     *
     * @param email the email to check
     * @return true if the email is valid, false otherwise
     */
    private boolean isValidEmailAddress(String email) {
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        }
        catch (AddressException e) {
            Logger.getLogger(UsersManager.class.getName()).log(Level.SEVERE, e.getMessage(), e);

            return false;
        }

        return true;
    }

    /**
     * Method to check if an user already exists in the database with the specified user name.
     *
     * @param userName user name that we want to check
     * @return true if the user already exists, false otherwise
     * @throws SQLException if an error occurred with the requests to the database
     */
    private boolean isUserExisting(String userName) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT COUNT(*) AS userExisting FROM user " +
                           "WHERE BINARY userName = ?;";

            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, userName);
            ResultSet rs = pstmt.executeQuery();

            rs.next();

            if (rs.getInt("userExisting") == 1) {
                return true;
            }

            return false;
        }
    }

    /**
     * Method to check if an other user than the specified user (id) himself already exists (user name) in the database.
     *
     * @param id id of the user himself in the database
     * @param userName the new user name that the user wants
     * @return true if the new user name already exists, false otherwise
     * @throws SQLException if an error occurred with the requests to the database
     */
    private boolean isOtherUserExisting(long id, String userName) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT COUNT(*) AS userExisting FROM user " +
                           "WHERE BINARY userName = ? AND id != ?;";

            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, userName);
            pstmt.setLong(2, id);
            ResultSet rs = pstmt.executeQuery();

            rs.next();

            if (rs.getInt("userExisting") == 1) {
                return true;
            }

            return false;
        }
    }
}
