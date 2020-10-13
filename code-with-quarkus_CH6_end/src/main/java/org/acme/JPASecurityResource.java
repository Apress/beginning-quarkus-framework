package org.acme;

import org.acme.entity.auth.AuthenticatedUser;
import org.acme.entity.auth.Role;

import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Path("/hello-jpa-security")
public class JPASecurityResource {

    @GET
    @Path("/{name:[a-zA-Z]*}/{password}/as-user")
    @Transactional
    public void newUser(final @PathParam("name") String name, final @PathParam("password") String password) throws ExecutionException, InterruptedException {
        Role role = Role.findAll().firstResult();
        AuthenticatedUser.add(name, password, role);
    }

    @GET
    @Path("/users")
    @Transactional
    public List<AuthenticatedUser> allUsers(){
        return AuthenticatedUser.findAll().list();
    }

}
