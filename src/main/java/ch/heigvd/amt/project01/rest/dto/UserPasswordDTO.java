package ch.heigvd.amt.project01.rest.dto;

/**
 * Created by sebbos on 15.10.2016.
 */
public class UserPasswordDTO {
    private String lastName;
    private String firstName;
    private String userName;
    private String password;

    public UserPasswordDTO() {

    }

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
