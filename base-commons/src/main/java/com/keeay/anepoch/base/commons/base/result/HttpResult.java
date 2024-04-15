package com.keeay.anepoch.base.commons.base.result;

import com.keeay.anepoch.base.commons.filter.TraceIdHelper;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

@Data
public class HttpResult<T> implements Serializable {
    private Integer code;
    private String message;
    private T data;
    private String traceId;

    public HttpResult() {
    }

    public HttpResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.traceId = TraceIdHelper.getCurrentTraceId();
    }

    public HttpResult(Integer code, String message, T data, String traceId) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.traceId = traceId;
    }

    public HttpResult(Integer code, T data) {
        this.code = code;
        this.data = data;
        this.traceId = TraceIdHelper.getCurrentTraceId();
    }

    public static <T> HttpResult<T> success(T data) {
        return new HttpResult<T>(200, "success", data);
    }

    public static <T> HttpResult<T> success(T data, String message) {
        return new HttpResult<T>(200, message, data);
    }

    public static <T> HttpResult<T> success(T data, String message, Integer code) {
        return new HttpResult<T>(code, message, data);
    }

    public static <T> HttpResult<T> failure(String message) {
        return new HttpResult<T>(-1, message, null);
    }

    public static <T> HttpResult<T> failure(Integer code, String message) {
        return new HttpResult<T>(code, message, null);
    }

    public static <T> HttpResult<T> failure(T data, String message) {
        return new HttpResult<T>(-1, message, data);
    }

    public static <T> HttpResult<T> failure(T data, String message, Integer code) {
        return new HttpResult<T>(code, message, data);
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
