package com.dropwizardrethinkdb.service.api;



import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.HashMap;


public class AuthToken {

 @NotNull
 @JsonProperty("id")
 private String id;

 @NotNull
 private String userId;

 @NotNull
 private String userRole;

 @NotNull
 private OffsetDateTime lastAccessUTC;

 /**
  * AuthToken model.
  *
  */
 public AuthToken(String authTokenId, String userId, String userRole, OffsetDateTime lastAccessUTC) {
  this.id = authTokenId;
  this.userId = userId;
  this.userRole = userRole;
  this.lastAccessUTC = lastAccessUTC;
 }

 public AuthToken(HashMap<String, String> authTokenResult) {

  if(authTokenResult != null){
   this.id = authTokenResult.getOrDefault("id", null);
   this.userId = authTokenResult.getOrDefault("userId", null);
   this.userRole = authTokenResult.getOrDefault("userRole", null);
   this.lastAccessUTC = OffsetDateTime.parse(String.valueOf(authTokenResult.get("lastAccessUTC")));
  }
 }

 public AuthToken() {

 }

 public AuthToken(String userId, String userRole, OffsetDateTime lastAccessUTC) {
  this.userId = userId;
  this.userRole = userRole;
  this.lastAccessUTC = lastAccessUTC;
 }

 public String getId() {
  return id;
 }

 public void setId(String id) {
  this.id = id;
 }

 public String getUserId() {
  return userId;
 }

 public String getUserRole() {  return userRole; }

 public void setUserRole(String userRole) {  this.userRole = userRole; }

 public void setUserId(String userId) {
  this.userId = userId;
 }

 public OffsetDateTime getLastAccessUTC() {
  return lastAccessUTC;
 }

 public void setLastAccessUTC(OffsetDateTime lastAccessUTC) {
  this.lastAccessUTC = lastAccessUTC;
 }
}
