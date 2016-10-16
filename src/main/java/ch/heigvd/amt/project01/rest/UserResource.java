package ch.heigvd.amt.project01.rest;

import ch.heigvd.amt.project01.model.User;
import ch.heigvd.amt.project01.rest.dto.GETUserDTO;
import ch.heigvd.amt.project01.rest.dto.POSTUserDTO;
import ch.heigvd.amt.project01.services.UsersManagerLocal;

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

import static java.util.stream.Collectors.toList;
import static javax.ws.rs.core.Response.created;
import static javax.ws.rs.core.Response.ok;

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
    @Produces(MediaType.APPLICATION_JSON)
    public List<GETUserDTO> getUsers(@QueryParam(value = "byName") String byName) {
        List<User> users = null;

        try {
            users = usersManager.findAllUsers();
        }
        catch (SQLException e) {
            Logger.getLogger(UserResource.class.getName()).log(Level.SEVERE, e.getMessage(), e);

            //return ok().build();
        }

        return users.stream()
                .filter(u -> byName == null || u.getLastName().equalsIgnoreCase(byName))
                .map(u -> toDTO(u))
                .collect(toList());
    }

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public GETUserDTO getUser(@PathParam(value = "id") long id) {
        User user = usersManager.loadUser(id);
        return toDTO(user);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(POSTUserDTO userDTO) {
        User user = fromDTO(userDTO);
        long userId = usersManager.saveUser(user);

        URI href = uriInfo.getBaseUriBuilder()
                .path(UserResource.class)
                .path(UserResource.class, "getUser")
                .build(userId);

        return created(href).build();
    }

    //// TODO: 15.10.2016 put sans id ???
    @Path("{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(POSTUserDTO userDTO, @PathParam(value = "id") long id) {
        User user = fromDTO(userDTO);
        
        usersManager.updateUser(id, user);

        return ok().build();
    }

    @Path("{id}")
    @DELETE
    public Response deleteUser(@PathParam(value = "id") long id) {
        usersManager.deleteUser(id);

        return ok().build();
    }

    public User fromDTO(POSTUserDTO userDTO) {
        return new User(userDTO.getLastName(), userDTO.getFirstName(), userDTO.getUserName(), userDTO.getPassword());
    }

    public GETUserDTO toDTO(User user) {
        return new GETUserDTO(user.getId(), user.getLastName(), user.getFirstName(), user.getUserName());
    }
}
