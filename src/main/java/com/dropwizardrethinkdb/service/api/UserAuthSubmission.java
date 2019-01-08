package com.dropwizardrethinkdb.service.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserAuthSubmission {

 @Email
 @JsonProperty("email")
 private String email;

 @NotNull
 @Size(min = 3, max = 40, message = "cannot be less than three characters or over forty.")
 @JsonProperty("password")
 private String password;

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
}
