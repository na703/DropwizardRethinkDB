package com.dropwizardrethinkdb.service.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserId {

 @NotNull
 @JsonProperty("id")
 @Size(min = 36, max = 36)
 private String id;

 /**
  * userId model.
  */
 public UserId(String id) {
  this.id = id;
 }

 public UserId() {
 }

 public String getId() {
  return id;
 }

 public void setId(String id) {
  this.id = id;
 }
}

