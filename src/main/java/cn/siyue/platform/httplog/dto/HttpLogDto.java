package cn.siyue.platform.httplog.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HttpLogDto {

    private String requestTime;
    private String responseTime;
    private String requestIp;
    private String requestUri;
    private String requestUrl;
    private Long userId;
    private String systemName;
    private Object payloadReqData;
    private Object formReqData;
    private Object responseData;
    private Boolean hasException = false;
    private String errorMsg;

}
