package com.dropwizardrethinkdb.service.helpers;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class SHA256 {

 public static String getHash(String string){
  return Hashing.sha256().hashString(string, StandardCharsets.UTF_8).toString();
 }

}
