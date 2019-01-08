package com.dropwizardrethinkdb.service.auth;

import com.dropwizardrethinkdb.service.api.User;
import io.dropwizard.auth.Authorizer;

public class CoreAuthorizer implements Authorizer<User> {

 @Override
 public boolean authorize(User user, String role) {

  return user.getUserRole() != null && user.getUserRole().equals(role);
 }
}
