


package com.keeay.anepoch.base.commons.exception;

import com.keeay.anepoch.base.commons.utils.ConditionUtils;
import com.google.common.collect.Range;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.Serializable;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

/**
 * @author ying.pan
 * @date 2019/7/8 10:59 AM.
 */
public enum SystemSegment implements Serializable {
    /**
     * 通用服务
     */
    COMMON(1),
    /**
     * 通用服务
     */
    INCOMMON(100),;
    /**
     * 系统code
     */
    private final int code;

    SystemSegment(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return reflectionToString(this, SHORT_PREFIX_STYLE);
    }

    /**
     * 有效的代码区间
     */
    private final static Range<Integer> VALID_CODE_RANGE = Range.closedOpen(1, 100000);

    /**
     * 使用方式SystemSegment.RESERVATION.buildErrorCode(1, "初始化错误");
     * <p>
     * ErrorCode.code = 1000001
     *
     * @param newValue it must be in [1, 100000), for example 1. The prefix is code.
     * @param message
     * @return
     */
    public ErrorCode buildErrorCode(int newValue, String message) {
        ConditionUtils.checkArgument(VALID_CODE_RANGE.contains(newValue), CommonErrorCode.INVALID_CODE_IN_ERROR_CODE);
        ConditionUtils.checkBlank(message, CommonErrorCode.INVALID_MESSAGE_IN_ERROR_CODE);
        String str = String.format("%05d", newValue);
        return ErrorCode.of(NumberUtils.toInt(code + str), message);
    }
}
