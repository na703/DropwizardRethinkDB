package com.dropwizardrethinkdb.service.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;


public class UserSubmission {

  @JsonProperty("firstName")
  @Size(min = 1, max = 50)
  private String firstName;

  @JsonProperty("lastName")
  @Size(min = 1, max = 50)
  private String lastName;

  @Email
  @JsonProperty("email")
  private String email;

  @JsonProperty("phoneNumber")
  @Pattern(regexp = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}",
          message = "Must be a valid US phone number. Brackets/dashes are allowed.")
  private String phoneNumber;

  @NotNull
  @Size(min = 3, max = 40, message = "cannot be less than three characters or over forty.")
  @JsonProperty("password")
  private String password;

  @JsonProperty("requireAccountActivation")
  @Pattern(regexp = "true|false",
          message = "Entities activation value can only be set to true or false.")
  private String requireAccountActivation;

  @JsonProperty("activationMode")
  @Pattern(regexp = "email|phone",
          message = "only email or phone supported.")
  private String activationMode;


  private String accountStatus;

  private String userRole;

  private String ipAddress;

  private String httpReferer;

  private long failedLoginCount;

  private String activationToken;

  private OffsetDateTime dateLastActivated;

  private OffsetDateTime lastLoginDate;

  private OffsetDateTime createdDate;

  private String createdBy;

  private OffsetDateTime updatedDate;

  private String updatedBy;

  /**
   * UserSubmission model.
  *
  */

  public UserSubmission() {

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

  public String getHttpReferer() {
    return httpReferer;
  }

  public void setHttpReferer(String httpReferer) {
    this.httpReferer = httpReferer;
  }

  public long getFailedLoginCount() {
    return failedLoginCount;
  }

  public void setFailedLoginCount(long failedLoginCount) {
    this.failedLoginCount = failedLoginCount;
  }

  public OffsetDateTime getLastLoginDate() {
    return lastLoginDate;
  }

  public void setLastLoginDate(OffsetDateTime lastLoginDate) {
    this.lastLoginDate = lastLoginDate;
  }

  public OffsetDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(OffsetDateTime createdDate) {
    this.createdDate = createdDate;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public OffsetDateTime getUpdatedDate() {
    return updatedDate;
  }

  public void setUpdatedDate(OffsetDateTime updatedDate) {
    this.updatedDate = updatedDate;
  }

  public String getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  public String getAccountStatus() {    return accountStatus;  }

  public void setAccountStatus(String accountStatus) {
    this.accountStatus = accountStatus;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getRequireAccountActivation() {    return requireAccountActivation;  }

  public void setRequireAccountActivation(String requireAccountActivation) {    this.requireAccountActivation = requireAccountActivation;  }

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

  public OffsetDateTime getDateLastActivated() {
    return dateLastActivated;
  }

  public void setDateLastActivated(OffsetDateTime dateLastActivated) {
    this.dateLastActivated = dateLastActivated;
  }

  public String getUserRole() {    return userRole;  }

  public void setUserRole(String userRole) {    this.userRole = userRole;  }
}
