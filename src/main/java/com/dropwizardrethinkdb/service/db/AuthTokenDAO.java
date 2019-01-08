package com.dropwizardrethinkdb.service.db;

import com.dropwizardrethinkdb.service.api.AuthToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;


public class AuthTokenDAO {

 private RethinkDBConn rethinkDBConn;
 Logger log = LoggerFactory.getLogger("UserDAO.java");

 public AuthTokenDAO(RethinkDBConn rethinkDBConn) {
  this.rethinkDBConn = rethinkDBConn;
 }


 public Optional<AuthToken> getAuthTokenById(final String authTokenId) {
  //ToDO: checkRethink for access token
  AuthToken authToken = new AuthToken();
  try {
   HashMap<String, String> result = rethinkDBConn.getR().table("auth_tokens").get(authTokenId).run(rethinkDBConn.getConn());
   authToken = new AuthToken(result);
  } catch (Exception ex) {
   log.error(ex.getMessage());
   throw new RuntimeException(ex.getMessage());
  }

  if (authToken == null) {
   return Optional.empty();
  }
  return Optional.of(authToken);

 }

 public AuthToken createNewAuthToken(final String userId, final String userRole){

  AuthToken authToken= new AuthToken(UUID.randomUUID().toString(), userId, userRole, OffsetDateTime.now());
  try {
   HashMap<String, ArrayList<String>> result = rethinkDBConn.getR().table("auth_tokens").insert(authToken).run(rethinkDBConn.getConn());

   if ((result.containsKey("generated_keys"))) {
    authToken.setId(result.get("generated_keys").get(0));
   }
  } catch (Exception ex) {
   log.error(ex.getMessage());
   throw new RuntimeException(ex.getMessage());
  }
  return authToken;
 }

 public void updateLastAccessTime(final String authTokenId){
  try {
   HashMap<String, String> result = rethinkDBConn.getR()
           .table("auth_tokens")
           .get(authTokenId)
           .update(rethinkDBConn.getR()
                   .hashMap("lastAccessUTC",OffsetDateTime.now()))
           .run(rethinkDBConn.getConn());
  } catch (Exception ex) {
   log.error(ex.getMessage());
   throw new RuntimeException(ex.getMessage());
  }

 }

}
