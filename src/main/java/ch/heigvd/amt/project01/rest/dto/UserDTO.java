package ch.heigvd.amt.project01.rest.dto;

/**
 * Class that represents the user's dto with its id. It is the used representation of a user for GET requests (REST API).
 * It is also important to say that we don't put the user's password in this class for security reasons.
 * We use another class named UserPasswordDTO in which we put the desired user's password when he wants to create or modify
 * his account information (POST and PUT requests in the REST API).
 *
 * @author Mathieu Urstein and Sébastien Boson
 */
public class UserDTO {
    private long id;
    private String lastName;
    private String firstName;
    private String userName;

    /**
     * Default constructor for the class UserDTO.
     */
    public UserDTO() {

    }

    /**
     * Constructor for the class UserDTO.
     *
     * @param id user's id
     * @param lastName user's last name
     * @param firstName user's first name
     * @param userName user name of the user
     */
    public UserDTO(long id, String lastName, String firstName, String userName) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.userName = userName;
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

    public void setId(long id) {
        this.id = id;
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
}
