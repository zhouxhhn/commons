/*
 * (C) Copyright 2018 Siyue Holding Group.
 */
package cn.siyue.platform.constants;

/**
 * 支付返回码
 */
public enum PaymentBackCode {
  SUCCESS(0, "成功"),
  ERROR(1, "出错"),
  REQUEST_PROCESS(2, "请求正在处理中"),
  INVALID_TRANSACTION(3, "无效交易"),
  INVALID_AMOUNT(4, "无效金额"),
  DENY_TRANSACTION(5, "不允许该人进行交易"),
  DUPLICATE_TRANSACTION(6, "重复交易"),
  SYSTEM_EXCEPTION(7, "系统异常、失效"),
  SYSTEM_TIMEOUT(8, "系统超时"),
  PARAM_ERROR(9, "参数错误"),
  ORDER_PAYED(10, "订单已支付"),
  ORDER_CLOSED(11, "订单已关闭"),
  ORDER_REVOKED(12, "订单已撤销"),
  PARAM_MISSING(13, "缺少参数"),
  ORDERNO_ERROR(14, "支付帐号错误"),
  REFUND_FAILURE(15, "退款请求失败"),
  ORDERNO_NOT_EXISTENCE(16, "此交易订单号不存在"),
  ORDER_NO_PAY(17, "订单未支付状态"),
  ORDER_PAY_TIMEOUT(18, "订单支付超时"),
  ORDER_PAY_ERROR(19, "订单支付失败"),
  ACCOUT_NOT_EXISTENCE(20, "该帐号不存在");

  /**
   * 返回码
   */
  private int value;

  /**
   * 返回说明
   */
  private String message;

  PaymentBackCode(int value, String message) {
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
