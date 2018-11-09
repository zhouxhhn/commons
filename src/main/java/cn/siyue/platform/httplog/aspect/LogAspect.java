package cn.siyue.platform.httplog.aspect;

import cn.siyue.platform.httplog.annotation.LogAnnotation;
import cn.siyue.platform.httplog.dto.HttpLogDto;
import cn.siyue.platform.util.IpUtils;
import cn.siyue.platform.util.JsonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author gavin
 * @description:
 */
public abstract class LogAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("@annotation(cn.siyue.platform.httplog.annotation.LogAnnotation)")
    public void recordLog(){

    }

    public abstract Long getUserId();

    public abstract String getSystemName();

    @Around("recordLog()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable{
        Object result = null;

        // 获取方法的返回类型,让缓存可以返回正确的类型
        //MethodSignature
        //Class returnType = ((MethodSignature) pjp.getSignature()).getReturnType();
        Object[] args = pjp.getArgs();
        HttpServletRequest request = findHttpServletRequest();

        Method method = ((MethodSignature)pjp.getSignature()).getMethod();
        LogAnnotation controllerAnno = method.getAnnotation(LogAnnotation.class);


        HttpLogDto httpLog = new HttpLogDto();
        try {
            if (request != null) {
                String requestURL = request.getRequestURL() != null ? request.getRequestURL().toString() : null;
                String requestURI = request.getRequestURI();
                String clientIp = IpUtils.getIpAddr(request);
                Long userId = getUserId();
                String systemName = getSystemName();

                httpLog.setRequestIp(clientIp);
                httpLog.setRequestTime(LocalDateTime.now().toString());
                httpLog.setUserId(userId);
                httpLog.setSystemName(systemName);
                httpLog.setRequestUrl(requestURL);
                httpLog.setRequestUri(requestURI);

                buildRequestData(request, httpLog, method);
            }

            //调用目标方法
            result = pjp.proceed();
            httpLog.setResponseTime(LocalDateTime.now().toString());

            if (result != null) {
                httpLog.setResponseData(result);
            }

        } catch(Throwable e) {
            LOGGER.error(e.getMessage(), e);
            httpLog.setHasException(true);
            httpLog.setErrorMsg(e.getMessage());
            if (StringUtils.isEmpty(httpLog.getResponseTime())) {
                httpLog.setResponseTime(LocalDateTime.now().toString());
            }
            throw e;
        } finally {
            try {
                String httpLogStr = JsonUtil.toJsonString(httpLog);
                LOGGER.info(String.format("接口调用日志：%n%s", httpLogStr));
            } catch (Throwable e) {
                LOGGER.error(e.getMessage(), e);
            }
        }

        return result;
    }

    /**
     * 移除request body中的第一层对象的属性和form data中的属性，不打印在日志中，但是还会传到controller
     * @return
     */
    public List<String> buildIgnoreProperties(String className, String methodName) {
        return null;
    }

    /**
     * 移除request body中的第一层对象的属性和form data中的属性，不打印在日志中，但是还会传到controller
     * @param reqJsonNode
     * @param reqMap
     */
    protected void ignoreProperties(JsonNode reqJsonNode, Map<String, String[]> reqMap, String className, String methodName) {
        List<String> propList = buildIgnoreProperties(className, methodName);
        if (propList != null && propList.size() > 0) {
            if (!JsonUtil.isNullNode(reqJsonNode) && reqJsonNode instanceof ObjectNode) {
                ObjectNode objNode = (ObjectNode)reqJsonNode;
                objNode.remove(propList);
            }

            if (reqMap != null && !reqMap.isEmpty()) {
                for (String key : propList) {
                    reqMap.remove(key);
                }
            }
        }
    }


    private void buildRequestData(HttpServletRequest request, HttpLogDto httpLog, Method method) {
        try {
            String content = getPayloadReqData(request);
            JsonNode jsonNode = null;
            if (StringUtils.isNotEmpty(content)) {

                try {
                    jsonNode = JsonUtil.jsonToObject(content, JsonNode.class);
                    httpLog.setPayloadReqData(jsonNode);
                } catch (Throwable e) {
                    LOGGER.error(e.getMessage(), e);
                    httpLog.setPayloadReqData(content);
                }

            }

            Map<String, String[]> map = request.getParameterMap();
            Map<String, String[]> reqDataMap = null;
            if (map != null && !map.isEmpty()) {
                reqDataMap = new HashMap<String, String[]>();
                Set<Map.Entry<String, String[]>> set = map.entrySet();
                for (Map.Entry<String, String[]> entry : set) {
                    reqDataMap.put(entry.getKey(), entry.getValue());
                }

                httpLog.setFormReqData(reqDataMap);
            }

            ignoreProperties(jsonNode, reqDataMap, method.getDeclaringClass().getName(), method.getName());

        } catch (Throwable e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private String getPayloadReqData(ServletRequest request) {
        BufferedReader br = null;
        try {
            br = request.getReader();
            String tmp;
            StringBuilder sb = new StringBuilder();
            while ((tmp = br.readLine()) != null) {
                sb.append(tmp);
            }
            return sb.toString();
        } catch (Throwable e) {
            LOGGER.error(e.getMessage(), e);
        }finally {
            if (br!=null){
                try {
                    br.close();
                } catch (Throwable e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
        return null;
    }

    private HttpServletRequest findHttpServletRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    private String toJsonString(Object pojo) {
        try {
            return JsonUtil.toJsonString(pojo);
        } catch (Throwable e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取被拦截方法对象 MethodSignature.getMethod() 获取的是顶层接口或者父类的方法对象 而缓存的注解在实现类的方法上 所以应该使用反射获取当前对象的方法对象
     */
    /*@SuppressWarnings("rawtypes")
    public Method getMethod(ProceedingJoinPoint pjp) {
        // 获取参数的类型
        Object[] args = pjp.getArgs();
        Class[] argTypes = new Class[pjp.getArgs().length];
        for (int i = 0; i < args.length; i++) {
            argTypes[i] = args[i].getClass();
        }
        Method method = null;
        try {
            method = pjp.getTarget().getClass().getMethod(pjp.getSignature().getName(), argTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return method;

    }*/
}
