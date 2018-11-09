package cn.siyue.platform.base;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class ResponseData<T> {

  // 定义jackson对象
  private static final ObjectMapper MAPPER = new ObjectMapper();

  // 响应业务状态
  private Integer code;

  // 响应消息
  private String msg;

  // 响应中的数据
  private T data;

  public static ResponseData build(Integer code, String msg, Object data) {
    return new ResponseData(code, msg, data);
  }

  public static ResponseData ok(Object data) {
    return new ResponseData(data);
  }

  public static ResponseData ok() {
    return new ResponseData(null);
  }

  public ResponseData() {

  }

  public static ResponseData build(Integer code, String msg) {
    return new ResponseData(code, msg, null);
  }

  public ResponseData(Integer code, String msg) {
    this.code = code;
    this.msg = msg;
    this.data = null;
  }

  public ResponseData(Integer code, String msg, T data) {
    this.code = code;
    this.msg = msg;
    this.data = data;
  }

  public ResponseData(T data) {
    this.code = 200;
    this.msg = "OK";
    this.data = data;
  }


  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  /**
   * 将json结果集转化为TaotaoResult对象
   *
   * @param jsonData json数据
   * @param clazz TaotaoResult中的object类型
   * @return
   */
  public static ResponseData formatToPojo(String jsonData, Class<?> clazz) {
    try {
      if (clazz == null) {
        return MAPPER.readValue(jsonData, ResponseData.class);
      }
      JsonNode jsonNode = MAPPER.readTree(jsonData);
      JsonNode data = jsonNode.get("data");
      Object obj = null;
      if (clazz != null) {
        if (data.isObject()) {
          obj = MAPPER.readValue(data.traverse(), clazz);
        } else if (data.isTextual()) {
          obj = MAPPER.readValue(data.asText(), clazz);
        }
      }
      return build(jsonNode.get("code").intValue(), jsonNode.get("msg").asText(), obj);
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * 没有object对象的转化
   *
   * @param json
   * @return
   */
  public static ResponseData format(String json) {
    try {
      return MAPPER.readValue(json, ResponseData.class);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Object是集合转化
   *
   * @param jsonData json数据
   * @param clazz 集合中的类型
   * @return
   */
  public static ResponseData formatToList(String jsonData, Class<?> clazz) {
    try {
      JsonNode jsonNode = MAPPER.readTree(jsonData);
      JsonNode data = jsonNode.get("data");
      Object obj = null;
      if (data.isArray() && data.size() > 0) {
        obj = MAPPER.readValue(data.traverse(),
                               MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
      }
      return build(jsonNode.get("code").intValue(), jsonNode.get("msg").asText(), obj);
    } catch (Exception e) {
      return null;
    }
  }
}
