package com.jpa_studentexample.exception;


import org.springframework.http.HttpStatus;

public class CustomException extends Exception{

  private static final long serialVersionUID = 1L;

  private String message;
  private HttpStatus status;

  public CustomException(String message, HttpStatus status){
     super(message);
     this.message = message;
     this.status = status;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public HttpStatus getStatus() {
    return status;
  }
}
