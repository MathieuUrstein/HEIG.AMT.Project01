package ch.heigvd.amt.projet01.model;

/**
 * Created by sebbos on 28.09.2016.
 */
public class User {
    private String userName;
    private String userPassword;

    public User(String userName, String password) {
        this.userName = userName;
        this.userPassword = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return userPassword;
    }
}
