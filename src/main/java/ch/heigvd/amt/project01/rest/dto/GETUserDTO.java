package ch.heigvd.amt.project01.rest.dto;

/**
 * Created by sebbos on 15.10.2016.
 */
public class GETUserDTO {
    private long id;
    private String lastName;
    private String firstName;
    private String userName;

    public GETUserDTO() {

    }

    public GETUserDTO(long id, String lastName, String firstName, String userName) {
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
