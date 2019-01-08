package com.dropwizardrethinkdb.service.resources;

import com.dropwizardrethinkdb.service.db.AccessDAO;
import com.dropwizardrethinkdb.service.db.UserDAO;
import com.dropwizardrethinkdb.service.api.*;
import com.codahale.metrics.annotation.Timed;
import io.dropwizard.auth.Auth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;


@Path("/user")
@Api(value="/user", description="An object for user CRUD operation.")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class UserResource {

  private UserDAO userDao;
  private AccessDAO accessDAO;
  Logger logger = LoggerFactory.getLogger("UserResource.java");

  public UserResource(UserDAO userDao, AccessDAO accessDAO) {
   this.userDao = userDao;
   this.accessDAO = accessDAO;
  }

  /**
   * creates a user.
   * @return id
   */
  @Timed()
  @POST()
  @ApiOperation(value="Create a user", notes="Provide basic demographic info to createUser a user.")
  @ApiResponses(value={
          @ApiResponse(code=201, message="User created successfully."),
          @ApiResponse(code=400, message="Invalid request."),
          @ApiResponse(code=422, message="Invalid request. Unprocessable Entity."),
          @ApiResponse(code=500, message="Internal Server Error."),
  })
  public Response create(@Valid UserSubmission userSubmission, @Context HttpServletRequest request) {

   UserId userId = null;

   //check if user with same email exists
   if(userDao.getUserByEmail(userSubmission.getEmail()).getId() != null){
    ErrorObject duplicateUser = new ErrorObject(HttpStatus.CONFLICT_409, "user with this email already exists.");
    return Response.status(HttpStatus.CONFLICT_409).entity(duplicateUser).build();
   }


   try {
    userId = userDao.createUser(userSubmission, request);
   } catch (UnsupportedEncodingException e) {
    e.printStackTrace();
    logger.error(e.getMessage());
   }

   if (userId.getId() == null) {
     ErrorObject errorObj = new ErrorObject(HttpStatus.BAD_REQUEST_400, "unable to createUser user. please retry.");
     return Response.status(HttpStatus.BAD_REQUEST_400).entity(errorObj).build();
    }
   //TODO: check activation verification
   if(userSubmission.getRequireAccountActivation().equals("true")){

   }

    return Response.status(HttpStatus.CREATED_201).entity(userId).build();
  }

 /**
  * update a user.
  * @return user
  */
 @Timed()
 @PUT()
 @ApiOperation(value="Update existing user", notes="Update existing fields for a user.")
 @ApiResponses(value={
         @ApiResponse(code=201, message="User updated successfully."),
         @ApiResponse(code=400, message="Invalid request."),
         @ApiResponse(code=422, message="Invalid request. Unprocessable Entity."),
         @ApiResponse(code=500, message="Internal Server Error."),
 })
 public Response updateUser(@Auth @Valid UserUpdate userUpdater, @Context HttpServletRequest request) {

  User user = null;
  User userByAccessObj = accessDAO.getUserObjectByToken(request);
  user = userDao.updateUserById(userUpdater, userByAccessObj.getId());
  if (user.getId() == null) {
   ErrorObject errorObj = new ErrorObject(HttpStatus.BAD_REQUEST_400, "unable to update user. please retry.");
   return Response.status(HttpStatus.BAD_REQUEST_400).entity(errorObj).build();
  }
  return Response.status(HttpStatus.CREATED_201).entity(user).build();
 }


 /**
  * return a user.
  * @return User
  */
 @Timed
 @GET
 @Path("/{id}")
 @ApiOperation(value = "Retrieve a user", notes = "Get a user by a user id (guid).")
 @ApiResponses(value = {
         @ApiResponse(code = 200, message = "User found. Success."),
         @ApiResponse(code = 404, message = "User not found."),
         @ApiResponse(code = 500, message = "Internal Server Error."),
 })
 public Response getUser(@Auth @Valid @PathParam("id") UserId userId) {

   User user = userDao.getUserById(userId.getId());
   if (user.getId() == null) {
    return Response.status(HttpStatus.NOT_FOUND_404).entity(user).build();
    }
   return Response.status(HttpStatus.OK_200).entity(user).build();

 }


 /**
  * delete a user.
  * @return message
  */

 @DELETE
 @Timed
 @Path("/{id}")
 @RolesAllowed("ADMIN")
 @ApiOperation(value = "Delete a user", notes = "Sets account Account to Deleted (soft delete).")
 @ApiResponses(value = {
         @ApiResponse(code = 200, message = "User deleted. Success."),
         @ApiResponse(code = 404, message = "User not found."),
         @ApiResponse(code = 304, message = "User has already been deleted."),
         @ApiResponse(code = 500, message = "Internal Server Error."),
 })
 public Response deleteUser(@Auth @Valid @PathParam("id") UserId userId) {

  User user = userDao.getUserById(userId.getId());
  if (user.getId() == null) {
   return Response.status(HttpStatus.NOT_FOUND_404).entity(user).build();
  }

  if (user.getAccountStatus().equals(Entities.Account.DELETED.toString())) {
   return Response.status(HttpStatus.NOT_MODIFIED_304).entity(user).build();
  }

  if(userDao.deleteUserById(userId.getId())){
   return Response.status(HttpStatus.OK_200).build();
  }

  ErrorObject errorObj = new ErrorObject(HttpStatus.INTERNAL_SERVER_ERROR_500, "Unable to delete user. please retry.");
  return Response.status(HttpStatus.BAD_REQUEST_400).entity(errorObj).build();

 }

}
