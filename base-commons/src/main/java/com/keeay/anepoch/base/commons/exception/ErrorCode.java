

package com.keeay.anepoch.base.commons.exception;

import java.io.Serializable;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

/**
 * @author py
 * @date 2019/4/15 7:39 PM.
 */
public class ErrorCode implements Serializable {
    /**
     * 参数异常
     */
    public final static int PARAMETER_ERROR_CODE = -1;
    /**
     * 运行时异常
     */
    public final static int RUNTIME_ERROR_CODE = -2;
    /**
     * code
     */
    private final int code;
    /**
     * message
     */
    private final String message;

    private ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void throwBizException() {
        throw new BizException(this);
    }


    public static ErrorCode of(int code, String message) {
        return new ErrorCode(code, message);
    }

    @Override
    public String toString() {
        return reflectionToString(this, JSON_STYLE);
    }
}
