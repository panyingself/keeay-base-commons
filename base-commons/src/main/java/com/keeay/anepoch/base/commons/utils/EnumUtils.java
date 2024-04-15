

package com.keeay.anepoch.base.commons.utils;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author ying.pan
 * @date 2019/7/8 6:01 PM.
 */
public class EnumUtils {
    /**
     * 获取枚举常量
     *
     * @param clazz
     * @param <F>
     * @return
     * @see EnumUtils#getEnumNames(Class)
     */
    public static <F extends Enum> List<F> getEnums(Class<F> clazz) {
        return Arrays.asList(clazz.getEnumConstants());
    }

    /**
     * 获取枚举列表的name()值
     * <p>
     * 使用方法:
     * getEnumNames(Weekday.class)
     *
     * @param clazz
     * @param <F>
     * @return
     */
    public static <F extends Enum> List<String> getEnumNames(Class<F> clazz) {
        return to(clazz, Enum::name);
    }

    /**
     * 对枚举列表进行转换
     * <p>
     * 参考例子
     *
     * @param clazz
     * @param mapper
     * @param <F>
     * @param <T>
     * @return
     * @see EnumUtils#getEnumNames(Class)
     */
    public static <F extends Enum, T> List<T> to(Class<F> clazz, Function<F, T> mapper) {
        return getEnums(clazz).stream().map(mapper).collect(Collectors.toList());
    }
}
