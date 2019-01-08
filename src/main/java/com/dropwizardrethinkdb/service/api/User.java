package com.dropwizardrethinkdb.service.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.*;

import org.hibernate.validator.constraints.*;

import java.security.Principal;
import java.util.HashMap;

public class User implements Principal {

 @NotNull
 private String id;

 @JsonProperty("firstName")
 private String firstName;

 @JsonProperty("lastName")
 private String lastName;

 @Email
 @JsonProperty("email")
 private String email;

 @JsonProperty("phoneNumber")
 @Pattern(regexp = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}",
         message = "Must be a valid US phone number. Brackets/dashes are allowed.")
 private String phoneNumber;

 @NotNull
 @JsonProperty("password")
 private String password;

 @JsonProperty("requireAccountActivation")
 @Pattern(regexp = "true|false",
         message = "Must be a valid US phone number. Brackets/dashes are also allowed.")
 private String requireAccountActivation;

 @JsonProperty("activationMode")
 @Pattern(regexp = "email|phone",
         message = "only email or phone supported.")
 private String activationMode;

 private String accountStatus;
 private String userRole;
 private String ipAddress;
 private String httpReferrer;
 private long failedLoginCount;
 private String activationToken;
 private String dateLastActivated;
 private String lastLoginDate;
 private String createdDate; //dates are string for reaonly purpose
 private String createdBy;
 private String updatedDate;
 private String updatedBy;


 /**
  * User model.
  */
 public User() {
 }

 public User(String id) {
  this.id = id;
 }

 public User(String id, String userRole) {
  this.id = id;
  this.userRole = userRole;
 }

 public User(HashMap<String, String> userResult) {
  if (userResult != null) {
   this.setFirstName(userResult.getOrDefault(Entities.UserKeys.firstName.toString(), null));
   this.setLastName(userResult.getOrDefault(Entities.UserKeys.lastName.toString(), null));
   this.setEmail(userResult.getOrDefault(Entities.UserKeys.email.toString(), null));
   this.setPhoneNumber(userResult.getOrDefault(Entities.UserKeys.phoneNumber.toString(), null));
   this.setPassword(userResult.getOrDefault(Entities.UserKeys.password.toString(), null));
   this.setId(userResult.getOrDefault(Entities.UserKeys.id.toString(), null));
   this.setIpAddress(userResult.getOrDefault(Entities.UserKeys.ipAddress.toString(), null));
   this.setAccountStatus(userResult.getOrDefault(Entities.UserKeys.accountStatus.toString(), null));
   this.setUserRole(userResult.getOrDefault(Entities.UserKeys.userRole.toString(), null));
   this.setHttpReferrer(userResult.getOrDefault(Entities.UserKeys.httpReferer.toString(), null));
   this.setFailedLoginCount(Long.valueOf(String.valueOf(userResult.get(Entities.UserKeys.failedLoginCount.toString()))));
   this.setRequireAccountActivation(userResult.getOrDefault(Entities.UserKeys.requireAccountActivation.toString(), null));
   this.setActivationMode(userResult.getOrDefault(Entities.UserKeys.activationMode.toString(), null));
   this.setActivationToken(userResult.getOrDefault(Entities.UserKeys.activationToken.toString(), null));
   this.setDateLastActivated(userResult.getOrDefault(Entities.UserKeys.dateLastActivated.toString(), null));
   this.setLastLoginDate(userResult.getOrDefault(Entities.UserKeys.lastLoginDate.toString(), null));
   this.setCreatedDate(String.valueOf(userResult.get(Entities.UserKeys.createdDate.toString())));
   this.setCreatedBy(userResult.getOrDefault(Entities.UserKeys.createdBy.toString(), null));
   this.setUpdatedDate(String.valueOf(userResult.get(Entities.UserKeys.updatedDate.toString())));
   this.setUpdatedBy(userResult.getOrDefault(Entities.UserKeys.updatedBy.toString(), null));
  }
 }

 /**
  * users model.
  */
 public User(String id, String firstName, String lastName, String email, String password, String phoneNumber) {
  this.id = id;
  this.firstName = firstName;
  this.lastName = lastName;
  this.email = email;
  this.phoneNumber = phoneNumber;
  this.password = password;
 }

 public String getId() {
  return id;
 }

 public void setId(String id) {
  this.id = id;
 }

 public String getFirstName() {
  return firstName;
 }

 public void setFirstName(String firstName) {
  this.firstName = firstName;
 }

 public String getLastName() {
  return lastName;
 }

 public void setLastName(String lastName) {
  this.lastName = lastName;
 }

 public String getEmail() {
  return email;
 }

 public void setEmail(String email) {
  this.email = email;
 }

 public String getPassword() {
  return password;
 }

 public void setPassword(String password) {
  this.password = password;
 }

 public String getIpAddress() {
  return ipAddress;
 }

 public void setIpAddress(String ipAddress) {
  this.ipAddress = ipAddress;
 }

 public String getHttpReferrer() {
  return httpReferrer;
 }

 public void setHttpReferrer(String httpReferrer) {
  this.httpReferrer = httpReferrer;
 }

 public long getFailedLoginCount() {
  return failedLoginCount;
 }

 public void setFailedLoginCount(long failedLoginCount) {
  this.failedLoginCount = failedLoginCount;
 }

 public String getLastLoginDate() {
  return lastLoginDate;
 }

 public void setLastLoginDate(String lastLoginDate) {
  this.lastLoginDate = lastLoginDate;
 }

 public void setCreatedDate(String createdDate) {
  this.createdDate = createdDate;
 }

 public String getCreatedBy() {
  return createdBy;
 }

 public String getCreatedDate() {
  return createdDate;
 }

 public void setCreatedBy(String createdBy) {
  this.createdBy = createdBy;
 }

 public String getUpdatedDate() {
  return updatedDate;
 }

 public void setUpdatedDate(String updatedDate) {
  this.updatedDate = updatedDate;
 }

 public String getUpdatedBy() {
  return updatedBy;
 }

 public void setUpdatedBy(String updatedBy) {
  this.updatedBy = updatedBy;
 }

 public String getAccountStatus() {
  return accountStatus;
 }

 public void setAccountStatus(String accountStatus) {
  this.accountStatus = accountStatus;
 }

 public String getPhoneNumber() {
  return phoneNumber;
 }

 public void setPhoneNumber(String phoneNumber) {
  this.phoneNumber = phoneNumber;
 }

 public String getRequireAccountActivation() {
  return requireAccountActivation;
 }

 public void setRequireAccountActivation(String requireAccountActivation) {
  this.requireAccountActivation = requireAccountActivation;
 }

 public String getActivationMode() {
  return activationMode;
 }

 public void setActivationMode(String activationMode) {
  this.activationMode = activationMode;
 }

 public String getActivationToken() {
  return activationToken;
 }

 public void setActivationToken(String activationToken) {
  this.activationToken = activationToken;
 }

 public String getDateLastActivated() {
  return dateLastActivated;
 }

 public void setDateLastActivated(String dateLastActivated) {
  this.dateLastActivated = dateLastActivated;
 }

 public String getUserRole() {  return userRole; }

 public void setUserRole(String userRole) {  this.userRole = userRole; }

 @Override
 @JsonIgnore
 public String getName() {
  return getEmail();
 }
}
