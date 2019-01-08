package com.dropwizardrethinkdb.service.resources;


import com.dropwizardrethinkdb.service.api.Person;
import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {


    @GET
    @Timed
    public Response SayHello(@QueryParam("name") String name) {
        Date date = new Date();
        if(name == null) name = "Stranger";
        String userGreeting = UUID.randomUUID().toString() + "@gmail.com";

        Person person = new Person(name,userGreeting);
        return Response.ok(person).build();
    }


}
