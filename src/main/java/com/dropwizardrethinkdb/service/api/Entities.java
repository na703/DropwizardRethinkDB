package com.dropwizardrethinkdb.service.api;

public class Entities {

 public enum Account {CREATED, ACTIVE, LOCKED, SUSPENDED, DEACTIVATED, DELETED }

 public enum Roles {SUPER_ADMIN, ADMIN, USER }

 public enum UserKeys {accountStatus, activationMode, activationToken, createdBy, createdDate, dateLastActivated, email,
  failedLoginCount, firstName, httpReferer, id, ipAddress, lastLoginDate, lastName, password, passwordSalt, phoneNumber,
  requireAccountActivation, updatedBy, updatedDate, userRole}

}
