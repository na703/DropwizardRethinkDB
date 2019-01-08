package com.dropwizardrethinkdb.service.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Size;

public class UserUpdate {


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
 @Size(min = 10, max = 25)
 private String phoneNumber;


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

 public String getPhoneNumber() {
  return phoneNumber;
 }

 public void setPhoneNumber(String phoneNumber) {
  this.phoneNumber = phoneNumber;
 }
}
