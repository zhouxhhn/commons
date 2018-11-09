/*
 * (C) Copyright 2018 Siyue Holding Group.
 */
package cn.siyue.platform.constants;

/**
 * 返回给前端的代码
 */
public enum  ResponseBackCode {

  SUCCESS(0, "成功"),

  ERROR_FAIL(1, "未知错误，失败"),

  ERROR_PARAM_EMPTY(101, "参数为必填"),

  ERROR_PARAM_INVALID(102, "参数无效"),

  ERROR_OBJECT_EXIST(201, "对象已存在"),

  ERROR_OBJECT_NOT_EXIST(202, "对象不存在"),

  ERROR_NEW_OBJECT(204, "新用户，请注册"),

  ERROR_USER_NOT_EXIST(203, "用户名不存在或密码错误"),

  ERROR_AUTH_FAIL(301, "未授权操作"),

  ERROR_PERMISSION_DENIED(302, "无权限操作"),

  ERROR_CREATE_FAIL(401, "创建失败"),

  ERROR_UPDATE_FAIL(402, "更新失败"),

  ERROR_DELETE_FAIL(403, "删除失败"),

  ERROR_NOT_FOUND(404,"资源没找到"),

  ERROR_TOKEN_TIMEOUT_CODE(405,"token过期或者无效"),

  ERROR_DOWNGRADE(406,"网络异常，请稍后重试"),

  ERROR_SERVER_ERROR_CODE(500,"服务器错误"),

  ERROR_CAPTCHA_INVALID(901, "验证码无效"),

  UNKNOWN_ERROR(902, "系统繁忙，请稍后再试...."),

  FILE_NOT_FOUND(903, "你要读取的资源找不到"),

  CUSTOM_NOTIFY(1024, "返回自定义提示");

  /**
   * 返回码
   */
  private int value;

  /**
   * 返回说明
   */
  private String message;

  ResponseBackCode(int value, String message) {
    this.value = value;
    this.message = message;
  }

  public int getValue() {
    return value;
  }

  public String getMessage() {
    return message;
  }
}
