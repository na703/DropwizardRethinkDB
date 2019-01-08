package com.dropwizardrethinkdb.service.auth;

import com.dropwizardrethinkdb.service.db.AuthTokenDAO;
import com.dropwizardrethinkdb.service.api.AuthToken;
import com.dropwizardrethinkdb.service.api.User;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

public class CoreAuthenticator implements Authenticator<String, User>{

 public static final int AUTH_TOKEN_EXPIRE_TIME_SECONDS = 3600;
 private AuthTokenDAO authTokenDAO;

 public CoreAuthenticator(AuthTokenDAO authTokenDAO) {
  this.authTokenDAO = authTokenDAO;
 }

 @Override
 public Optional<User> authenticate(String authTokenId) throws AuthenticationException {

  /* validate string as a UUID */
  UUID authTokenIdValid;
  try {
   authTokenIdValid = UUID.fromString(authTokenId);
  } catch (IllegalArgumentException e) {
   return Optional.empty();
  }

  /* Retrieve access token from DB */
  Optional<AuthToken> authToken = authTokenDAO.getAuthTokenById(authTokenId);
  if (authToken == null || !authToken.isPresent()) {
   return Optional.empty();
  }

  /* check if access token expired*/
  Duration duration = Duration.between(authToken.get().getLastAccessUTC(), OffsetDateTime.now());
  if (duration.getSeconds() > AUTH_TOKEN_EXPIRE_TIME_SECONDS) {
   return Optional.empty();
  }

  /* slide access token expiration */
  authTokenDAO.updateLastAccessTime(authTokenId);

  /* Return the user object for processing */
  return Optional.of(new User(authToken.get().getUserId(), authToken.get().getUserRole()));
 }

}
