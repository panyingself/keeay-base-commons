

package com.keeay.anepoch.base.commons.utils;

import com.keeay.anepoch.base.commons.exception.ErrorCode;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

/**
 * @author ying.pan
 * @date 2019/7/8 6:02 PM.
 */
public class LengthUtils {
    public static <T> void equals(Collection<T> collections, int size, ErrorCode errorCode) {
        equals(CollectionUtils.size(collections), size, errorCode);
    }

    public static <T> void lessThan(Collection<T> collections, int size, ErrorCode errorCode) {
        lessThan(CollectionUtils.size(collections), size, errorCode);
    }

    public static <T> void moreThan(Collection<T> collections, int size, ErrorCode errorCode) {
        moreThan(CollectionUtils.size(collections), size, errorCode);
    }

    public static <T> void lessAndEquals(Collection<T> collections, int size, ErrorCode errorCode) {
        lessAndEquals(CollectionUtils.size(collections), size, errorCode);
    }

    public static <T> void moreAndEquals(Collection<T> collections, int size, ErrorCode errorCode) {
        moreAndEquals(CollectionUtils.size(collections), size, errorCode);
    }

    public static void lessThan(String content, int size, ErrorCode errorCode) {
        equals(StringUtils.length(content), size, errorCode);
    }

    public static void lessAndEquals(String content, int size, ErrorCode errorCode) {
        lessAndEquals(StringUtils.length(content), size, errorCode);
    }

    public static void equals(String content, int size, ErrorCode errorCode) {
        equals(StringUtils.length(content), size, errorCode);
    }

    public static void moreThan(String content, int size, ErrorCode errorCode) {
        moreThan(StringUtils.length(content), size, errorCode);
    }

    public static void moreAndEquals(String content, int size, ErrorCode errorCode) {
        moreAndEquals(StringUtils.length(content), size, errorCode);
    }

    public static void lessThan(int left, int right, ErrorCode errorCode) {
        ConditionUtils.checkArgument(left < right, errorCode);
    }

    public static void lessAndEquals(int left, int right, ErrorCode errorCode) {
        ConditionUtils.checkArgument(left <= right, errorCode);
    }

    public static void equals(int left, int right, ErrorCode errorCode) {
        ConditionUtils.checkArgument(left == right, errorCode);
    }

    public static void moreThan(int left, int right, ErrorCode errorCode) {
        ConditionUtils.checkArgument(left > right, errorCode);
    }

    public static void moreAndEquals(int left, int right, ErrorCode errorCode) {
        ConditionUtils.checkArgument(left >= right, errorCode);
    }
}
