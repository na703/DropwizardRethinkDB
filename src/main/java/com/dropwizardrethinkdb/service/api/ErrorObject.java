package com.dropwizardrethinkdb.service.api;

public class ErrorObject {

 private int code;
 private String errorMessage;

 /**
  * ErrorObject model.
  *
  */
 public ErrorObject() {
 }
 public ErrorObject(int code, String errorMessage) {
  this.code = code;
  this.errorMessage = errorMessage;
 }

 public int getCode() {
  return code;
 }

 public void setCode(int code) {
  this.code = code;
 }

 public String getErrorMessage() {
  return errorMessage;
 }

 public void setErrorMessage(String errorMessage) {
  this.errorMessage = errorMessage;
 }
}
