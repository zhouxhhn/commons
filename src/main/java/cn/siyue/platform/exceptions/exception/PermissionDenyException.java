/*
 * (C) Copyright 2018 Siyue Holding Group.
 */
package cn.siyue.platform.exceptions.exception;

public class PermissionDenyException extends  BaseException{

  public PermissionDenyException(int code, String feedback) {
    super(code, feedback);
  }
}
