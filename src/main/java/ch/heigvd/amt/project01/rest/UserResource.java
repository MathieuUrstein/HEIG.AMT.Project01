package ch.heigvd.amt.project01.rest;

import ch.heigvd.amt.project01.model.User;
import ch.heigvd.amt.project01.rest.dto.UserDTO;
import ch.heigvd.amt.project01.rest.dto.UserPasswordDTO;
import ch.heigvd.amt.project01.services.dao.UsersManagerLocal;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ch.heigvd.amt.project01.util.Utility.MAX_USER_INPUT_SIZE;
import static java.util.stream.Collectors.toList;
import static javax.ws.rs.core.Response.*;

/**
 * Class that represents a specific resource in our REST API (../api/users).
 * It contains several methods that will be called with special HTTP request patterns.
 *
 * @author Mathieu Urstein and Sébastien Boson
 */
@Stateless
@Path("/users")
public class UserResource {
    @EJB
    private UsersManagerLocal usersManager;

    @Context
    UriInfo uriInfo;

    /**
     * Method called when a GET request is made on our REST API with the following pattern: .../api/users.
     *
     * @param byName eventually the name given in parameter of the GET request for sorting the returned users
     * @return a specific HTTP status code if an error has occurred or the list of desired users (information about)
     *         in a JSON format
     */
    @GET
    public Response getUsers(@QueryParam(value = "byName") String byName) {
        List<User> users;

        try {
            users = usersManager.findAllUsers();
        }
        catch (Exception e) {
            // if an unexpected error has occurred
            Logger.getLogger(UserResource.class.getName()).log(Level.SEVERE, e.getMessage(), e);

            return serverError().build();
        }

        List<UserDTO> usersDTO = users.stream()
                .filter(u -> byName == null || u.getLastName().equalsIgnoreCase(byName))
                .map(u -> toDTO(u))
                .collect(toList());
        // everything is correct
        return ok(usersDTO, MediaType.APPLICATION_JSON).build();
    }

    /**
     * Method called when a GET request is made on our REST API with the following pattern: .../api/users/{id}.
     * In which the id is specified by the client who made the GET request.
     *
     * @param id the specific id of an user in the database
     * @return a specific HTTP status code if an error has occurred or the user (information about) in a JSON format
     */
    @Path("{id}")
    @GET
    public Response getUser(@PathParam(value = "id") long id) {
        User user;

        try {
            if (!usersManager.isUserExisting(id)) {
                // the user (id) doesn't exist
                return status(Status.NOT_FOUND).build();
            }

            user = usersManager.loadUser(id);
        }
        catch (Exception e) {
            // if an unexpected error has occurred
            Logger.getLogger(UserResource.class.getName()).log(Level.SEVERE, e.getMessage(), e);

            return serverError().build();
        }

        // everything is correct
        return ok(toDTO(user), MediaType.APPLICATION_JSON).build();
    }

    /**
     * Method called when a POST request is made on our REST API with the following pattern: .../api/users.
     *
     * @param userDTO class that contains all the information about the user that the client wants to create (deserialization
     *                of the JSON information given by the client)
     * @return a specific HTTP status code if an error has occurred or the link (URI) of the new user that the client has created
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(UserPasswordDTO userDTO) {
        long userId;

        try {
            userId = usersManager.saveUser(fromDTO(userDTO));
        }
        catch (IllegalArgumentException e) {
            // exception with the inputs of the client (missing parameters, empty parameters or too long parameters)
            Logger.getLogger(UserResource.class.getName()).log(Level.SEVERE, e.getMessage(), e);

            return status(Status.BAD_REQUEST).build();
        }
        catch (Exception e) {
            Logger.getLogger(UserResource.class.getName()).log(Level.SEVERE, e.getMessage(), e);

            if (e.getCause() != null && e.getCause().getClass().getSimpleName().equals(IllegalArgumentException.class.getSimpleName())) {
                // exception with the inputs of the client (user already exists)
                return status(Status.CONFLICT).build();
            }

            // other problems
            return serverError().build();
        }

        // everything is correct
        // we build the new URI with the base given URI
        URI href = uriInfo.getBaseUriBuilder()
                .path(UserResource.class)
                .path(UserResource.class, "getUser")
                .build(userId);

        return created(href).build();
    }

    /**
     * Method called when a PUT request is made on our REST API with the following pattern: .../api/users/{id}.
     * In which the id is specified by the client who made the PUT request.
     * It is important to say that the PUT request must be used for a full update.
     *
     * @param userDTO class that contains all the information about the user that the client wants to update (deserialization
     *                of the JSON information given by the client)
     * @param id the specific id of the user that the client wants to update in the database
     * @return a specific HTTP status code if an error has occurred or if everything is correct
     */
    @Path("{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(UserPasswordDTO userDTO, @PathParam(value = "id") long id) {
        try {
            if (!usersManager.isUserExisting(id)) {
                // the user (id) doesn't exist
                return status(Status.NOT_FOUND).build();
            }

            usersManager.updateUser(id, fromDTO(userDTO));
        }
        catch (IllegalArgumentException e) {
            // exception with the inputs of the client (missing parameters, empty parameters or too long parameters)
            Logger.getLogger(UserResource.class.getName()).log(Level.SEVERE, e.getMessage(), e);

            return status(Status.BAD_REQUEST).build();
        }
        catch (Exception e) {
            Logger.getLogger(UserResource.class.getName()).log(Level.SEVERE, e.getMessage(), e);

            if (e.getCause() != null && e.getCause().getClass().getSimpleName().equals(IllegalArgumentException.class.getSimpleName())) {
                // exception with the inputs of the client (new user name already exists)
                return status(Status.CONFLICT).build();
            }

            // other problems
            return serverError().build();
        }

        // everything is correct
        return ok().build();
    }

    /**
     * Method called when a DELETE request is made on our REST API with the following pattern: .../api/users/{id}.
     * In which the id is specified by the client who made the DELETE request.
     *
     * @param id the specific id of the user that the client wants to delete in the database
     * @return a specific HTTP status code if an error has occurred or if everything is correct
     */
    @Path("{id}")
    @DELETE
    public Response deleteUser(@PathParam(value = "id") long id) {
        try {
            if (!usersManager.isUserExisting(id)) {
                // the user (id) doesn't exist
                return status(Status.NOT_FOUND).build();
            }

            usersManager.deleteUser(id);
        }
        catch (Exception e) {
            Logger.getLogger(UserResource.class.getName()).log(Level.SEVERE, e.getMessage(), e);

            // other problems
            return serverError().build();
        }

        // everything is correct
        return ok().build();
    }

    /**
     * Method to transform a DTO object to a User object (model).
     *
     * @param userDTO class that we want to transform
     * @return a new User object
     * @throws IllegalArgumentException if the information in the DTO object are incorrect
     */
    private User fromDTO(UserPasswordDTO userDTO) throws IllegalArgumentException {
        // we must give all the parameters in the received JSON information
        if (userDTO.getLastName() == null || userDTO.getFirstName() == null || userDTO.getUserName() == null ||
                userDTO.getPassword() == null) {
            throw new IllegalArgumentException("All the parameters are required!");
        }

        // user name and password parameters can't ben empty
        if (userDTO.getUserName().isEmpty() || userDTO.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Username and password can't be empty!");
        }

        // check entries size
        if (userDTO.getLastName().length() > MAX_USER_INPUT_SIZE || userDTO.getFirstName().length() > MAX_USER_INPUT_SIZE ||
                userDTO.getUserName().length() > MAX_USER_INPUT_SIZE || userDTO.getPassword().length() > MAX_USER_INPUT_SIZE) {
            throw new IllegalArgumentException("One or several entries are too long (max " + MAX_USER_INPUT_SIZE + " characters)!");
        }

        return new User(userDTO.getLastName(), userDTO.getFirstName(), userDTO.getUserName(), userDTO.getPassword());
    }

    /**
     * Method to transform a User object to a DTO object.
     *
     * @param user the User object that we want to transform
     * @return the new DTO object
     */
    private UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getLastName(), user.getFirstName(), user.getUserName());
    }
}
