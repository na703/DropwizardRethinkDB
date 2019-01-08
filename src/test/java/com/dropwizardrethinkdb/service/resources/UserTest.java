package com.dropwizardrethinkdb.service.resources;


import com.dropwizardrethinkdb.service.api.User;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class UserTest {
 static String user1Id;
 @Before
 public void bootStrapUser(){
  user1Id = UUID.randomUUID().toString();

 }

 @Test
 public void getUserByIdTest(){
  User user1 = new User(user1Id, "Joe", "Smith","joe.smith@corp.com","12345", "0000000000");
  assertEquals(user1.getId(),user1Id);

 }

}