package ch.heigvd.amt.project01.model;

/**
 * Created by sebbos on 28.09.2016.
 */
public class User {
    private long id;
    private String lastName;
    private String firstName;
    private String userName;
    private String password;

    public User(long id, String lastName, String firstName, String userName, String password) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.userName = userName;
        this.password = password;
    }

    public User(String lastName, String firstName, String userName, String password) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.userName = userName;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
