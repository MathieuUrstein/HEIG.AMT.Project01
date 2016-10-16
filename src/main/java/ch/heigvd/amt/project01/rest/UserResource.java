package ch.heigvd.amt.project01.rest;

import ch.heigvd.amt.project01.model.User;
import ch.heigvd.amt.project01.rest.dto.GETUserDTO;
import ch.heigvd.amt.project01.rest.dto.POSTUserDTO;
import ch.heigvd.amt.project01.services.UsersManagerLocal;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ch.heigvd.amt.project01.util.Utility.toJSON;
import static java.util.stream.Collectors.toList;
import static javax.ws.rs.core.Response.*;

/**
 * Created by sebbos on 15.10.2016.
 */
@Stateless
@Path("/users")
public class UserResource {
    @EJB
    private UsersManagerLocal usersManager;

    @Context
    UriInfo uriInfo;

    @GET
    public Response getUsers(@QueryParam(value = "byName") String byName) {
        List<User> users;

        try {
            users = usersManager.findAllUsers();
        }
        catch (SQLException e) {
            Logger.getLogger(UserResource.class.getName()).log(Level.SEVERE, e.getMessage(), e);

            return serverError().build();
        }

        List<GETUserDTO> usersDTO = users.stream()
                                         .filter(u -> byName == null || u.getLastName().equalsIgnoreCase(byName))
                                         .map(u -> toDTO(u))
                                         .collect(toList());

        return returnJSONResponse(usersDTO);
    }

    @Path("{id}")
    @GET
    public Response getUser(@PathParam(value = "id") long id) {
        User user;

        try {
            user = usersManager.loadUser(id);
        }
        catch (Exception e) {
            Logger.getLogger(UserResource.class.getName()).log(Level.SEVERE, e.getMessage(), e);

            if (e.getCause() != null && e.getCause().getClass().getSimpleName().equals("IllegalArgumentException")) {
                // exception with the input of the client
                return status(Status.NOT_FOUND).build();
            }
            else {
                // other exceptions
                return serverError().build();
            }
        }

        return returnJSONResponse(toDTO(user));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(POSTUserDTO userDTO) {
        User user = fromDTO(userDTO);
        long userId;

        try {
            userId = usersManager.saveUser(user);
        }
        catch (Exception e) {
            Logger.getLogger(UserResource.class.getName()).log(Level.SEVERE, e.getMessage(), e);

            if (e.getCause() != null && e.getCause().getClass().getSimpleName().equals("IllegalArgumentException")) {
                // exception with the input of the client
                return status(Status.CONFLICT).build();
            }
            else {
                // other exceptions
                return serverError().build();
            }
        }

        URI href = uriInfo.getBaseUriBuilder()
                .path(UserResource.class)
                .path(UserResource.class, "getUser")
                .build(userId);

        return created(href).build();
    }

    @Path("{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(POSTUserDTO userDTO, @PathParam(value = "id") long id) {
        User user = fromDTO(userDTO);

        try {
            usersManager.updateUser(id, user);
        }
        catch (Exception e) {
            Logger.getLogger(UserResource.class.getName()).log(Level.SEVERE, e.getMessage(), e);

            if (e.getCause() != null && e.getCause().getClass().getSimpleName().equals("IllegalArgumentException")) {
                // exception with the input (id) of the client
                return status(Status.NOT_FOUND).build();
            }
            else if (e.getClass().getSimpleName().equals("MySQLIntegrityConstraintViolationException")) {
                // exception with the input (new user name) of the client
                return status(Status.CONFLICT).build();
            }
            else {
                // other exceptions
                return serverError().build();
            }
        }

        return ok().build();
    }

    @Path("{id}")
    @DELETE
    public Response deleteUser(@PathParam(value = "id") long id) {
        try {
            usersManager.deleteUser(id);
        }
        catch (Exception e) {
            Logger.getLogger(UserResource.class.getName()).log(Level.SEVERE, e.getMessage(), e);

            if (e.getCause() != null && e.getCause().getClass().getSimpleName().equals("IllegalArgumentException")) {
                // exception with the input of the client
                return status(Status.NOT_FOUND).build();
            }
            else {
                // other exceptions
                return serverError().build();
            }
        }

        return ok().build();
    }

    private Response returnJSONResponse(Object object) {
        String json;

        try {
            json = toJSON(object);
        }
        catch (JsonProcessingException e) {
            Logger.getLogger(UserResource.class.getName()).log(Level.SEVERE, e.getMessage(), e);

            return serverError().build();
        }

        return ok(json, MediaType.APPLICATION_JSON).build();
    }

    private User fromDTO(POSTUserDTO userDTO) {
        return new User(userDTO.getLastName(), userDTO.getFirstName(), userDTO.getUserName(), userDTO.getPassword());
    }

    private GETUserDTO toDTO(User user) {
        return new GETUserDTO(user.getId(), user.getLastName(), user.getFirstName(), user.getUserName());
    }
}
