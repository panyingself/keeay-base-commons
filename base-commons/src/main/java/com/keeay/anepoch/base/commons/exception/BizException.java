

package com.keeay.anepoch.base.commons.exception;

/**
 * @author py
 * @date 2019/4/15 7:38 PM.
 */
public class BizException extends RuntimeException {
    /**
     * 异常对象
     */
    private final ErrorCode errorCode;

    public BizException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BizException(String errorMessage) {
        super(errorMessage);
        this.errorCode = ErrorCode.of(-1, errorMessage);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
