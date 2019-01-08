package com.dropwizardrethinkdb.service.db;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.dropwizardrethinkdb.service.api.*;
import com.rethinkdb.net.Cursor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;


public class UserDAO {

 private RethinkDBConn rethinkDBConn;

 Logger log = LoggerFactory.getLogger("UserDAO.java");

 public UserDAO(RethinkDBConn rethinkDBConn){
  this.rethinkDBConn = rethinkDBConn;
 }

 public User getUserById(String id) {

  User user;
    try {
    HashMap<String, String> result = rethinkDBConn.getR().table("users").get(id).run(rethinkDBConn.getConn());
    user = new User(result);
    } catch (Exception ex) {
    log.error(ex.getMessage());
    throw new RuntimeException(ex.getMessage());
  }
  return user;
 }

 public UserId createUser(UserSubmission userSubmission, HttpServletRequest req) throws UnsupportedEncodingException {

  OffsetDateTime currDateTime = OffsetDateTime.now();
  UserId userId = new UserId();


  userSubmission.setCreatedDate(currDateTime);
  userSubmission.setUpdatedDate(currDateTime);
  userSubmission.setCreatedBy(userSubmission.getEmail());
  userSubmission.setUpdatedBy(userSubmission.getEmail());
  userSubmission.setFailedLoginCount(0);
  userSubmission.setActivationToken(UUID.randomUUID().toString());
  userSubmission.setIpAddress(req.getRemoteAddr());
  userSubmission.setHttpReferer(req.getHeader("referer"));
  userSubmission.setAccountStatus(getAccountStatusByActivationChoice(userSubmission.getRequireAccountActivation()).toString());
  userSubmission.setUserRole(Entities.Roles.ADMIN.toString());
  String bcryptedPassword = BCrypt.with(BCrypt.Version.VERSION_2Y).hashToString(12, userSubmission.getPassword().toCharArray());
  //https://github.com/patrickfav/bcrypt
  userSubmission.setPassword(bcryptedPassword);

  try {
   HashMap<String, ArrayList<String>> result = rethinkDBConn.getR().table("users").insert(userSubmission).run(rethinkDBConn.getConn());

   if ((result.containsKey("generated_keys"))) {
    userId.setId(result.get("generated_keys").get(0));
   }
  } catch (Exception ex) {
   log.error(ex.getMessage());
   throw new RuntimeException(ex.getMessage());
  }


  return userId;
  }

 public boolean deleteUserById(String id){

  int actualDeletedQuantity = 1;
  int expectedDeletedQuantity = 1;
  try {
   HashMap<String, String> result = rethinkDBConn.getR()
           .table("users")
           .get(id)
           .update(rethinkDBConn.getR()
                   .hashMap(Entities.UserKeys.accountStatus.toString(),Entities.Account.DELETED.toString())
                   .with(Entities.UserKeys.updatedDate.toString(),OffsetDateTime.now()))
           .run(rethinkDBConn.getConn());
   if ((result.containsKey("replaced"))) {
    actualDeletedQuantity = Integer.valueOf(String.valueOf(result.get("replaced")));
   }
  } catch (Exception ex) {
   log.error(ex.getMessage());
   throw new RuntimeException(ex.getMessage());
  }

  if(actualDeletedQuantity == expectedDeletedQuantity){
   return true;
  }
  return false;
 }

 public User getUserByEmail(String emailAddr){
  User user = new User();
  try {
   Cursor cursor =
           rethinkDBConn.getR().table("users")
           .getAll(emailAddr).optArg("index", Entities.UserKeys.email.toString())
           //.filter(rethinkDBConn.getR().hashMap("email", emailAddr))
           //.filter(row -> row.g("email").eq(email))
           .limit(1)
           .run(rethinkDBConn.getConn());
   if(cursor.hasNext()) {
    HashMap<String, String> result = (HashMap<String, String>) cursor.next();
    user = new User(result);
   }
  } catch (Exception ex) {
   ex.printStackTrace();
   log.error(ex.getMessage());
   //throw new RuntimeException(ex.getMessage());
  }
  return user;
 }

 public Optional<User> getUserByCredentials(final String email, final String password) {

  User user = this.getUserByEmail(email);
  if(user.getId() != null) {
   BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
   if(result.verified) {
    return Optional.of(user);
   }

  }

  return Optional.empty();
 }

 public User updateUserById(UserUpdate userUpdate, String id) {

  User user = this.getUserById(id);
  if(user.getId() != null) {

   int actualUpdatedQuantity = 1;
   int expectedUpdatedQuantity = 1;
   try {
    HashMap<String, String> result = rethinkDBConn.getR()
            .table("users")
            .get(id)
            .update(rethinkDBConn.getR()
                    .hashMap(Entities.UserKeys.firstName.toString(),userUpdate.getFirstName())
                    .with(Entities.UserKeys.lastName.toString(),userUpdate.getLastName())
                    .with(Entities.UserKeys.email.toString(),userUpdate.getEmail())
                    .with(Entities.UserKeys.phoneNumber.toString(),userUpdate.getPhoneNumber())
                    .with(Entities.UserKeys.updatedDate.toString(),OffsetDateTime.now())
                    .with(Entities.UserKeys.updatedBy.toString(),userUpdate.getEmail())
            )
            .run(rethinkDBConn.getConn());
    if ((result.containsKey("replaced"))) {
     actualUpdatedQuantity = Integer.valueOf(String.valueOf(result.get("replaced")));
    }
   } catch (Exception ex) {
    log.error(ex.getMessage());
    throw new RuntimeException(ex.getMessage());
   }
   if(actualUpdatedQuantity != expectedUpdatedQuantity){
    throw new RuntimeException("unable to update user.");
   }
  }
  return user;
  }

  private Entities.Account getAccountStatusByActivationChoice(String choice){

  String userSelection = choice.toLowerCase();
   if ((userSelection.equals("false"))) {
    //no activation is required
    return Entities.Account.ACTIVE;
   } else {
    return Entities.Account.CREATED;
   }
  }
}
