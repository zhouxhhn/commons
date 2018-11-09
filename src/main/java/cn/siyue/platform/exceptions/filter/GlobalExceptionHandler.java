/*
 * (C) Copyright 2018 Siyue Holding Group.
 */
package cn.siyue.platform.exceptions.filter;

import cn.siyue.platform.base.ResponseData;
import cn.siyue.platform.constants.ResponseBackCode;
import cn.siyue.platform.exceptions.exception.BaseException;
import cn.siyue.platform.exceptions.exception.PermissionDenyException;
import cn.siyue.platform.exceptions.exception.RequestException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 处理全局controller类中的异常
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * 处理请求异常
   */
  @ExceptionHandler(RequestException.class)
  @ResponseBody
  ResponseData RequestException(RequestException e) {
    return ResponseData.build(e.getCode(),e.getFeedback());
  }

  /**
   * 处理无授权
   */
  @ExceptionHandler(PermissionDenyException.class)
  @ResponseBody
  ResponseData permissionException(PermissionDenyException e) {
    return ResponseData.build(e.getCode(),e.getFeedback());
  }

  /**
   * 处理BaseException异常
   */
  @ExceptionHandler(BaseException.class)
  @ResponseBody
  ResponseData baseException(BaseException e) {
    return ResponseData.build(e.getCode(),e.getFeedback());
  }

  /**
   * 处理所有不可知的异常
   */
  @ExceptionHandler(Exception.class)
  @ResponseBody
  ResponseData handleUnException(Exception e) {
    return ResponseData.build(
        ResponseBackCode.ERROR_SERVER_ERROR_CODE.getValue(),
        ResponseBackCode.ERROR_SERVER_ERROR_CODE.getMessage(),e.getMessage()
    );
  }
}