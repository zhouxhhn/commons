/*
 * (C) Copyright 2018 Siyue Holding Group.
 */
package cn.siyue.platform.exceptions.exception;

public class RequestException extends  BaseException{

  public RequestException(int code, String feedback) {
    super(code, feedback);
  }
}
