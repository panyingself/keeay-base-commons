


package com.keeay.anepoch.base.commons.lang;

import com.google.common.collect.*;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * 安全包装，避免NPE
 *
 * @author ying.pan
 * @date 2019/7/8 3:53 PM.
 */
public class Safes {
    public static <K, V> Map<K, V> of(Map<K, V> source) {
        return Optional.ofNullable(source).orElseGet(() -> Maps.newHashMapWithExpectedSize(0));
    }

    public static <T> Iterator<T> of(Iterator<T> source) {
        return Optional.ofNullable(source).orElseGet(() -> Lists.<T>newArrayListWithCapacity(0).iterator());
    }

    public static <T> Iterable<T> of(Iterable<T> source) {
        return Optional.ofNullable(source).orElseGet(() -> Lists.newArrayListWithCapacity(0));
    }

    public static <T> List<T> of(List<T> source) {
        return Optional.ofNullable(source).orElseGet(() -> Lists.newArrayListWithCapacity(0));
    }

    public static <T> Set<T> of(Set<T> source) {
        return Optional.ofNullable(source).orElseGet(() -> Sets.newHashSetWithExpectedSize(0));
    }

    public static <R, C, V> Table<R, C, V> of(Table<R, C, V> source) {
        return Optional.ofNullable(source).orElseGet(() -> HashBasedTable.create(0, 0));
    }

    public static BigDecimal of(BigDecimal source) {
        return Optional.ofNullable(source).orElse(BigDecimal.ZERO);
    }

    public static String of(String source) {
        return Optional.ofNullable(source).orElse(StringUtils.EMPTY);
    }

    public static String of(String source, String defaultStr) {
        return Optional.ofNullable(source).orElse(defaultStr);
    }
}
