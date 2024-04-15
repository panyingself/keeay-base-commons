


package com.keeay.anepoch.base.commons.exception;

import java.io.Serializable;

/**
 * @author ying.pan
 * @date 2019/7/8 10:58 AM.
 */
public class CommonErrorCode implements Serializable {
    private static ErrorCode build(int code, String message) {
        return SystemSegment.COMMON.buildErrorCode(code, message);
    }

    public final static ErrorCode INVALID_CODE_IN_ERROR_CODE =
            build(1, "Error code is illegal! It must be in [1,10000)");

    public final static ErrorCode INVALID_MESSAGE_IN_ERROR_CODE = build(2, "Message is blank!");

    public final static ErrorCode ARRAY_IS_EMPTY = build(3, "Array is empty!");
}
