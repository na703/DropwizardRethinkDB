package com.dropwizardrethinkdb.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class RethinkConfig {

 @JsonProperty("host")
 @NotEmpty
 public String RethinkHost;

 @JsonProperty("port")
 @Min(1)
 @Max(65535)
 public int RethinkPort;

 @JsonProperty("db")
 @NotEmpty
 public String RethinkDatabase;

 @JsonProperty("defaultTable")
 @NotEmpty
 public String defaultTable;


 public String getRethinkHost() {
  return RethinkHost;
 }

 public int getRethinkPort() {
  return RethinkPort;
 }

 public String getRethinkDatabase() {
  return RethinkDatabase;
 }

 public String getDefaultTable() {  return defaultTable; }
}
