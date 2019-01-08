package com.dropwizardrethinkdb.service;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.hibernate.validator.constraints.*;

public class ExampleServiceConfiguration extends Configuration {
 @JsonProperty("RethinkDB")
 public RethinkConfig rethinkdb = new RethinkConfig();

 @JsonProperty("swagger")
 public SwaggerBundleConfiguration swaggerBundleConfiguration;

 @JsonProperty("authenticationCachePolicy")
 @NotEmpty
 public String authenticationCachePolicy;

 public String getAuthenticationCachePolicy() {
  return authenticationCachePolicy;
 }
}
