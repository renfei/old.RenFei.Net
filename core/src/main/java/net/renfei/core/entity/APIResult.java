package net.renfei.core.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class APIResult<T> {
    private APIResult() {
    }

    /**
     * 是否成功
     */
    private Boolean success;
    /**
     * 消息信息
     */
    private String message;
    /**
     * 状态码
     */
    private int code = 200;
    /**
     * 响应返回的数据对象
     */
    private T data;
    /**
     * 接口当前版本号
     */
    private String version = "v1.0.0";
    /**
     * 响应时间戳
     */
    private Long timestamp;

    public static APIResult fillResult() {
        return fillResult(true, "", null);
    }

    public static APIResult fillResult(boolean success) {
        if (success)//Success
            return fillResult(success, "Success!", null);
        else
            return fillResult(success, "Fail!", null);
    }

    public static APIResult fillResult(boolean success, String message) {
        return fillResult(success, message, null);
    }

    public static APIResult fillResult(boolean success, String message, Object data) {
        APIResult apiResult = new APIResult();
        apiResult.setSuccess(success);
        apiResult.setMessage(message);
        apiResult.setData(data);
        apiResult.setTimestamp(System.currentTimeMillis());
        return apiResult;
    }
}
