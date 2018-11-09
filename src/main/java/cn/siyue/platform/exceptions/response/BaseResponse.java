/*
 * (C) Copyright 2018 Siyue Holding Group.
 */
package cn.siyue.platform.exceptions.response;

import cn.siyue.platform.constants.ResponseBackCode;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public abstract class BaseResponse implements Serializable {

  int code = ResponseBackCode.SUCCESS.getValue();

  String message;
}
