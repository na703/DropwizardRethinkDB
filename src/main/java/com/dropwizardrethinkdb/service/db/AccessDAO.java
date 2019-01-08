package com.dropwizardrethinkdb.service.db;

import com.dropwizardrethinkdb.service.api.AuthToken;
import com.dropwizardrethinkdb.service.api.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class AccessDAO {

 private AuthTokenDAO authTokenDAO;
 private UserDAO userDAO;

 public AccessDAO(AuthTokenDAO authTokenDAO, UserDAO userDAO) {
  this.authTokenDAO = authTokenDAO;
  this.userDAO = userDAO;
 }

 public User getUserObjectByToken(HttpServletRequest request){

  User user = new User();
  String bearerToken = request.getHeader("Authorization").replace("Bearer ", "");
  Optional<AuthToken> authToken = authTokenDAO.getAuthTokenById(bearerToken);

  if(authToken.isPresent()) {
   user = userDAO.getUserById(authToken.get().getUserId());
  }
  return user;
 }

}
