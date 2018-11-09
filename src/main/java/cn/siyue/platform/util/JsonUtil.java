package cn.siyue.platform.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * json处理工具
 * 
 * @author gavin
 * 
 *         2017-6-29
 */
public final class JsonUtil {
	private static final Logger LOG = LoggerFactory.getLogger(JsonUtil.class); 
	private JsonUtil() {}
	
	/**
	 * ObjectMapper 提供单例供全局使用
	 */
	
	private static class SingletonHolder{
		private static ObjectMapper mapper;
		static{
			mapper = new ObjectMapper();
			//设置日期对象的输出格式
			mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINESE));
			
			//设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			//禁止使用int代表Enum的order()来反序列化Enum
			mapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, true);
			
			//设置  null 自动转换成 ""
			/*mapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>(){
				@Override
				public void serialize(Object value, JsonGenerator jgen,SerializerProvider provider) throws IOException,JsonProcessingException {
					jgen.writeString("");
				}
			});*/
		}
	}
	
	private static ObjectMapper getMapper() {
		return SingletonHolder.mapper;
	}

	/**
	 * 将对象转换为json字符串
	 * @param pojo
	 * @return
	 * @throws IOException
	 */
	public static String toJsonString(Object pojo) throws IOException {
        if (pojo==null){
            return null;
        }
		try {
			return getMapper().writeValueAsString(pojo);
		} catch (IOException e) {
			LOG.error("pojo parse  json string error",e);
			throw e;
		}
	}

	/**
	 * 将字符串转换为json对象
	 * @param input
	 * @return
	 * @throws IOException
	 */
	public static JsonNode parseJson(String input) throws IOException {
		if (input==null) {
			return null;
		}
		try {
			return getMapper().readTree(input);
		} catch (IOException e) {
			LOG.error("json processing error,input: "+input,e);
			throw e;
		}
	}

	
	/**
	 * 将inputStream 转换为json对象
	 * @param in
	 * @return
	 * @throws IOException
	 */
	/*public static JsonNode getJsonNodefromStream(InputStream in) throws IOException {
		try {
			return getMapper().readTree(in);
		} catch (JsonProcessingException e) {
			LOG.error("json processing error",e);
			throw e;
		} catch (IOException e) {
			LOG.error("read file error",e);
			throw e;
		}
	}*/

	/**
	 * 将json字符串转换为java对象，只支持返回简单对象（非集合类型）
	 * @param jsonString
	 * @param valueType
	 * @return
	 * @throws IOException
	 */
	public static  <T> T jsonToObject(String jsonString, Class<T> valueType) throws IOException {
        if (StringUtils.hasText(jsonString)){
            return getMapper().readValue(jsonString, valueType);
        }
	    return null;
    }

	 /**
	 * 将json字符串转为集合类型 List、Map等
	 * @param jsonStr json字符串
	 * @param collectionClass 集合类型
	 * @param elementClasses 泛型类型
	 */
	public static <T> T jsonToObject(String jsonStr, Class<?> collectionClass, Class<?>... elementClasses) throws IOException {
		if (!StringUtils.hasText(jsonStr)) {
			return null;
		}
		JavaType javaType = getMapper().getTypeFactory().constructParametricType(collectionClass, elementClasses);
		return getMapper().readValue(jsonStr, javaType);
	}
	
	public static <T> T jsonToObject(JsonNode node, Class<?> collectionClass, Class<?>... elementClasses) throws IOException {
		if (node==null) {
			return null;
		}
		JavaType javaType = getMapper().getTypeFactory().constructParametricType(collectionClass, elementClasses);
		String jsonStr = getMapper().writeValueAsString(node);
		return getMapper().readValue(jsonStr, javaType);
	}
	
	/*public static <T> T fastjsonToObject(String json,Class<?>... elementClasses){
		if (json==null) {
			return null;
		}
		T t = (T) JSONObject.parseObject(json, elementClasses.getClass());
		
		return  t;
	}
	
	public static String fastJsonToString(Object object){
		if (object == null) {
			return null;
		}
		return JSONObject.toJSONString(object);
	}*/


    public static boolean isNullNode(JsonNode node) {
		if (node == null || node.isNull() || node.isMissingNode())
			return true;
		
		return false;
	}
    
    /**
	 * 将JsonNode转换为java对象，只支持返回简单对象（非集合类型）
	 * @param node
	 * @param valueType
	 * @return
	 * @throws IOException
	 */
	public static  <T> T jsonToObject(JsonNode node, Class<T> valueType) throws IOException {
        if (!isNullNode(node)){
			String jsonStr = getMapper().writeValueAsString(node);
            return getMapper().readValue(jsonStr, valueType);
        }
	    return null;
    }

}
