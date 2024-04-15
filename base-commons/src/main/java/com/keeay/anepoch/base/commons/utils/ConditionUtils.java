

package com.keeay.anepoch.base.commons.utils;

import com.keeay.anepoch.base.commons.exception.BizException;
import com.keeay.anepoch.base.commons.exception.CommonErrorCode;
import com.keeay.anepoch.base.commons.exception.ErrorCode;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * @author ying.pan
 * @date 2019/7/2 9:01 PM.
 */
public class ConditionUtils {
    public ConditionUtils() {
    }

    /**
     * 如果obj为null，则抛BizException异常.
     *
     * @param obj
     * @param errorCode
     * @param <T>
     * @return
     */
    public static <T> T checkNotNull(T obj, ErrorCode errorCode) {
        checkArgument(Objects.nonNull(obj), errorCode);
        return obj;
    }

    /**
     * 判断obj是否为空，如果为空，则根据errorCode中的message和objects形成message。
     * 如，
     * ErrorCode invalidHotelName = ErrorCode.of(1, "Hotel name %s is invalid!");
     * <p>
     * String hotelName = null;
     * ConditionUtils.notNullWithFormat(hotelName, invalidHotelName, hotelName);
     *
     * @param obj
     * @param errorCode
     * @param objects
     * @param <T>
     * @return
     */
    public static <T> T notNullWithFormat(T obj, ErrorCode errorCode, Object... objects) {
        checkArgumentWithFormat(Objects.nonNull(obj), errorCode, objects);
        return obj;
    }

    /**
     * 判断obj是否为空，如果为空，则根据errorCode中的message和objects形成message。
     * 如，
     * ErrorCode invalidHotelName = ErrorCode.of(1, "Hotel name %s is invalid!");
     * <p>
     * String hotelName = null;
     * ConditionUtils.checkArgumentWithFormat(StringUtils.isNotBlank(hotelName), invalidHotelName, hotelName);
     *
     * @param b
     * @param errorCode
     * @param objects
     * @return
     */
    public static void checkArgumentWithFormat(boolean b, ErrorCode errorCode, Object... objects) {
        checkArgument(b, format(errorCode, objects));
    }

    /**
     * 判断argument，如果为false，则根据errorCode中的object和message抛出异常
     *
     * @param b
     * @param errorCode
     */
    public static void checkArgument(boolean b, ErrorCode errorCode) {
        if (!b) {
            errorCode.throwBizException();
        }

    }

    /**
     * 判断argument，如果为false，直接抛出异常message
     *
     * @param b
     * @param message
     */
    public static void checkArgument(boolean b, String message) {
        if (!b) {
            throw new BizException(message);
        }

    }

    /**
     * 检查集合是否为空
     *
     * @param collections
     * @param errorCode
     * @param <T>
     */
    public static <T> void checkEmpty(Collection<T> collections, ErrorCode errorCode) {
        checkArgument(CollectionUtils.isNotEmpty(collections), errorCode);
    }

    /**
     * 检查map是否为空
     *
     * @param map
     * @param errorCode
     * @param <K>
     * @param <V>
     */
    public static <K, V> void checkEmpty(Map<K, V> map, ErrorCode errorCode) {
        checkArgument(MapUtils.isNotEmpty(map), errorCode);
    }

    /**
     * 检查字符串是否为null、""或" "
     *
     * @param str
     * @param errorCode
     */
    public static void checkBlank(String str, ErrorCode errorCode) {
        checkArgument(StringUtils.isNotBlank(str), errorCode);
    }

    private static void moreThanOne(Object... objects) {
        checkArgument(ArrayUtils.getLength(objects) > 0, CommonErrorCode.ARRAY_IS_EMPTY);
    }

    public static ErrorCode format(ErrorCode errorCode, Object... objects) {
        moreThanOne(objects);
        return ErrorCode.of(errorCode.getCode(), String.format(errorCode.getMessage(), objects));
    }
}
