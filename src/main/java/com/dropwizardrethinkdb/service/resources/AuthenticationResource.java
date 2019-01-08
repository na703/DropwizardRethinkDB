package com.dropwizardrethinkdb.service.resources;

import com.dropwizardrethinkdb.service.db.AuthTokenDAO;
import com.dropwizardrethinkdb.service.db.UserDAO;
import com.dropwizardrethinkdb.service.api.*;
import org.eclipse.jetty.http.HttpStatus;


import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/auth/token")
public class AuthenticationResource {
 private AuthTokenDAO authTokenDAO;
 private UserDAO userDAO;


 public AuthenticationResource(AuthTokenDAO authTokenDAO, UserDAO userDAO) {
  this.authTokenDAO = authTokenDAO;
  this.userDAO = userDAO;
 }

 @POST
 @Produces(MediaType.APPLICATION_JSON)
 @Consumes(MediaType.APPLICATION_JSON)
 public Response doAuthentication(@Valid UserAuthSubmission userAuthSubmission){
  ErrorObject errorObj = new ErrorObject();
  /* find user by credentials */
  Optional<User> user = userDAO.getUserByCredentials(userAuthSubmission.getEmail(), userAuthSubmission.getPassword());
  if (user == null || !user.isPresent()) {
   errorObj.setCode(HttpStatus.BAD_REQUEST_400);
   errorObj.setErrorMessage("Invalid email or password combination.");
   throw new WebApplicationException(Response.status(HttpStatus.BAD_REQUEST_400).entity(errorObj).build());
  }
  /*User found, validate business logic */
  if(!(user.get().getAccountStatus().equals(Entities.Account.ACTIVE.toString()))) {
   errorObj.setCode(HttpStatus.FORBIDDEN_403);
   errorObj.setErrorMessage("Login failed. Entities isn't active.");
   throw new WebApplicationException(Response.status(HttpStatus.BAD_REQUEST_400).entity(errorObj).build());
  }
  /*mint a new token and return it. */
  AuthToken authToken = authTokenDAO.createNewAuthToken(user.get().getId(), user.get().getUserRole());
  return Response.status(HttpStatus.OK_200).entity(authToken).build();
 }
}
