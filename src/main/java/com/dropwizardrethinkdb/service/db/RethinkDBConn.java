package com.dropwizardrethinkdb.service.db;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;

public class RethinkDBConn {


 private RethinkDB r;
 private Connection conn;

 public RethinkDBConn(RethinkDB r, Connection conn) {
  this.r = r;
  this.conn = conn;
 }

 public RethinkDB getR() {
  return r;
 }

 public Connection getConn() {
  return conn;
 }
}
