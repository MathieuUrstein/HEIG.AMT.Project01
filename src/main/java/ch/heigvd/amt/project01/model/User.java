package ch.heigvd.amt.project01.model;

/**
 * Class that represents the user's model (data).
 *
 * @author Mathieu Urstein and Sébastien Boson
 */
public class User {
    private long id;
    private String lastName;
    private String firstName;
    private String userName;
    private String password;

    /**
     * Constructor with id of the class User.
     *
     * @param id user's id
     * @param lastName user's last name
     * @param firstName user's first name
     * @param userName user name of the user
     * @param password user's password
     */
    public User(long id, String lastName, String firstName, String userName, String password) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.userName = userName;
        this.password = password;
    }

    /**
     * Constructor without id of the class User.
     *
     * @param lastName user's last name
     * @param firstName user's first name
     * @param userName user name of the user
     * @param password user's password
     */
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
