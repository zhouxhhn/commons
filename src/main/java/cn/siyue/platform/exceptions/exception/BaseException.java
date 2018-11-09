/*
 * (C) Copyright 2018 Siyue Holding Group.
 */
package cn.siyue.platform.exceptions.exception;

import cn.siyue.platform.constants.ResponseBackCode;

import org.springframework.http.HttpStatus;

/**
 * 异常基类，各个模块的运行期异常均继承该类
 */
public class BaseException extends RuntimeException {

  private int code = ResponseBackCode.ERROR_FAIL.getValue();

  private String feedback;

  private HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

  public BaseException(int code, String feedback) {
    super(feedback);
    this.code = code;
    this.feedback = feedback;
  }

  public BaseException(int code, String feedback, HttpStatus httpStatus) {
    super(feedback);
    this.code = code;
    this.feedback = feedback;
    this.httpStatus = httpStatus;
  }

  public int getCode() {
    return code;
  }

  public String getFeedback() {
    return feedback;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }
}
