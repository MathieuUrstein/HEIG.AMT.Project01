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

import static ch.heigvd.amt.project01.util.Utility.MAX_USER_ENTRY_SIZE;

/**
 * Created by sebbos on 10.10.2016.
 */
@Stateless
public class UsersManager implements UsersManagerLocal {
    @Resource(lookup = "java:/jdbc/project01")
    private DataSource dataSource;

    //// TODO: 18.10.2016 script pour que tout soit automatique avec docker-compose + cp intégré
    //// TODO: 17.10.2016 specify how passwords are handled

    @Override
    public void createUser(String lastName, String firstName, String userName, String password, String passwordConfirmation)
            throws IllegalArgumentException, SQLException {
        if (userName.isEmpty() || password.isEmpty() || passwordConfirmation.isEmpty()) {
            throw new IllegalArgumentException("Username, password and password confirmation can't be empty!");
        }

        // check if user entries are not too long (> MAX_USER_ENTRY_SIZE)
        checkUserEntriesSize(lastName, firstName, userName, password);

        // check if the user name is a valid email address
        if (!isValidEmailAddress(userName)) {
            throw new IllegalArgumentException("Invalid email address!");
        }

        if (!password.equals(passwordConfirmation)) {
            throw new IllegalArgumentException("Password confirmation doesn't match password!");
        }

        if (isUserExisting(userName)) {
            throw new IllegalArgumentException("The specified username already exists!");
        }

        insertionUserDB(new User(lastName, firstName, userName, password));
    }

    @Override
    public void loginUser(String userName, String password) throws IllegalArgumentException, SQLException {
        if (userName.isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException("Username and password can't be empty!");
        }

        // check if the user name is a valid email address
        if (!isValidEmailAddress(userName)) {
            throw new IllegalArgumentException("Invalid email address!");
        }

        if (!isUserExisting(userName)) {
            throw new IllegalArgumentException("The specified username doesn't exist!");
        }

        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT password FROM user " +
                           "WHERE userName = ?;";

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
    public User loadUser(long id) throws IllegalArgumentException, SQLException {
        User user;

        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT * FROM user " +
                           "WHERE id = ?;";

            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            // if the specified user (with its id) doesn't exist
            if (!rs.next()) {
                throw new IllegalArgumentException("The specified user (id) doesn't exist!");
            }

            user = new User(id, rs.getString("lastName"), rs.getString("firstName"), rs.getString("userName"), rs.getString("password"));
        }

        return user;
    }

    @Override
    public long saveUser(User user) throws IllegalArgumentException, SQLException {
        if (isUserExisting(user.getUserName())) {
            throw new IllegalArgumentException("The specified username already exists!");
        }

        return insertionUserDB(user);
    }

    @Override
    public void updateUser(long id, User user) throws IllegalArgumentException, SQLException {
        // case if user (id) doesn't exist
        if (!isUserExisting(id)) {
            throw new IllegalArgumentException("The specified user (id) doesn't exist!");
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
    public void deleteUser(long id) throws IllegalArgumentException, SQLException {
        // case if user (id) doesn't exist
        if (!isUserExisting(id)) {
            throw new IllegalArgumentException("The specified user (id) doesn't exist!");
        }

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

    private void checkUserEntriesSize(String lastName, String firstName, String userName, String password) throws IllegalArgumentException {
        if (lastName.length() > MAX_USER_ENTRY_SIZE) {
            throw new IllegalArgumentException("Last name is too long (max " + MAX_USER_ENTRY_SIZE + " characters)!");
        }
        else if (firstName.length() > MAX_USER_ENTRY_SIZE) {
            throw new IllegalArgumentException("First name is too long (max " + MAX_USER_ENTRY_SIZE + " characters)!");
        }
        else if (userName.length() > MAX_USER_ENTRY_SIZE) {
            throw new IllegalArgumentException("User name is too long (max " + MAX_USER_ENTRY_SIZE + " characters)!");
        }
        else if (password.length() > MAX_USER_ENTRY_SIZE) {
            throw new IllegalArgumentException("Password is too long (max " + MAX_USER_ENTRY_SIZE + " characters)!");
        }
    }

    private boolean isValidEmailAddress(String email) {
        boolean valid = true;

        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        }
        catch (AddressException e) {
            Logger.getLogger(UsersManager.class.getName()).log(Level.SEVERE, e.getMessage(), e);

            valid = false;
        }

        return valid;
    }

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

    private boolean isUserExisting(long id) throws SQLException {
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

    private long insertionUserDB(User user) throws SQLException {
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
}
