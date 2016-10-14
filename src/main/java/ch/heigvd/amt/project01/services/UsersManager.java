package ch.heigvd.amt.project01.services;

import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sebbos on 10.10.2016.
 */
@Singleton
public class UsersManager implements UsersManagerLocal {
    @Resource(lookup="java:/jdbc/project01")
    private DataSource dataSource;

    // mise à jour future => hasher les mots de passe :)

    @Override
    public void createUser(String lastName, String firstName, String userName, String password, String passwordConfirmation)
            throws IllegalArgumentException, SQLException {
        if (userName.isEmpty() || password.isEmpty() || passwordConfirmation.isEmpty()) {
            throw new IllegalArgumentException("Username, password and password confirmation can't be empty!");
        }

        if (!password.equals(passwordConfirmation)) {
            throw new IllegalArgumentException("Password confirmation doesn't match password!");
        }

        if (isUserExisting(userName)) {
            throw new IllegalArgumentException("The specified username already exists!");
        }

        try (Connection connection = dataSource.getConnection()) {
            String request = "INSERT INTO user (lastName, firstName, userName, password) VALUES ('" +
                             lastName + "', '" + firstName + "', '" + userName + "', '" + password + "');";

            PreparedStatement pstmt = connection.prepareStatement(request);

            if (pstmt.executeUpdate() != 1) {
                throw new SQLException("The insertion of a new user in the database failed!");
            }
        }
    }

    @Override
    public void loginUser(String userName, String password) throws IllegalArgumentException, SQLException {
        if (userName.isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException("Username and password can't be empty!");
        }

        if (!isUserExisting(userName)) {
            throw new IllegalArgumentException("The specified username doesn't exist!");
        }

        try (Connection connection = dataSource.getConnection()) {
            String request = "SELECT password FROM user " +
                             "WHERE userName = '" + userName + "';";

            PreparedStatement pstmt = connection.prepareStatement(request);
            ResultSet rs = pstmt.executeQuery();

            rs.next();

            if (!(rs.getString("password").equals(password))) {
                throw new IllegalArgumentException("The specified password is incorrect!");
            }
        }
    }

    private boolean isUserExisting(String userName) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            String request = "SELECT COUNT(*) AS userExisting FROM user " +
                             "WHERE userName = '" + userName + "';";

            PreparedStatement pstmt = connection.prepareStatement(request);
            ResultSet rs = pstmt.executeQuery();

            rs.next();

            if (rs.getInt("userExisting") == 1) {
                return true;
            }

            return false;
        }
    }
}
