package ch.heigvd.amt.project01.rest.dto;

/**
 * Class that represents the user's dto without its id but with its password. It is the used representation of a user for
 * POST and PUT requests (REST API).
 *
 * @author Mathieu Urstein and Sébastien Boson
 */
public class UserPasswordDTO {
    private String lastName;
    private String firstName;
    private String userName;
    private String password;

    /**
     * Default constructor for the class UserPasswordDTO.
     */
    public UserPasswordDTO() {

    }

    /**
     * Constructor for the class UserPasswordDTO.
     *
     * @param lastName user's last name
     * @param firstName user's first name
     * @param userName user name of the user
     * @param password user's password
     */
    public UserPasswordDTO(String lastName, String firstName, String userName, String password) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.userName = userName;
        this.password = password;
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

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
