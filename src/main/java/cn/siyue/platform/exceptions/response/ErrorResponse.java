/*
 * (C) Copyright 2018 Siyue Holding Group.
 */
package cn.siyue.platform.exceptions.response;

public class ErrorResponse extends BaseResponse {

  public ErrorResponse(int code, String message) {
    this.code = code;
    this.message = message;
  }
}
