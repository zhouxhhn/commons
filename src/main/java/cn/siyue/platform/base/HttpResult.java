/*
 * (C) Copyright 2018 Siyue Holding Group.
 */
package cn.siyue.platform.base;

import lombok.Data;

@Data
public class HttpResult {

  // 响应的状态码
  private int code;

  // 响应的响应体
  private String body;

  public HttpResult(int code, String body){
    this.code = code;
    this.body = body;
  }


}
