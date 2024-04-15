

package com.keeay.anepoch.base.commons.base.helper;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @author ying.pan
 * @date 2019/7/8 4:51 PM.
 */
public class EnumHelper<T extends Enum<T>> {

    public final static Set<String> METHOD_NAMES = ImmutableSet.of("code", "getCode", "getIntValue");

    private Class<T> ecls = null;

    private Map<Integer, T> codeMap = null;

    private Map<String, T> nameMap = null;

    private Method codeMethod = null;

    private EnumHelper(Class<T> ecls) {
        this.ecls = ecls;
    }

    public EnumHelper initCodeMap() {
        codeMap = getCodeMap(this.ecls);
        return this;
    }

    public EnumHelper initNameMap() {
        nameMap = getNameMap(this.ecls);
        return this;
    }

    public static <T extends Enum<T>> EnumHelper of(Class<T> cls) {
        return new EnumHelper(cls);
    }

    public Method getCodeMethod() {
        return codeMethod;
    }

    /**
     * 获取code->Enum的映射关系
     *
     * @return
     */
    private Map<Integer, T> getCodeMap(Class<T> value) {
        Map<Integer, T> codeMapping = Maps.newHashMap();
        Optional<Method> method = METHOD_NAMES.stream()
                .map(name -> ReflectionUtils.findMethod(value, name))
                .filter(Objects::nonNull)
                .findFirst();
        codeMethod = method.orElseThrow(() -> new IllegalStateException(value.getClass()
                + "#code():int method or getCode() or getIntValue():int is required! "));
        for (T t : Arrays.asList(value.getEnumConstants())) {
            Integer code = (Integer) ReflectionUtils.invokeMethod(codeMethod, t);
            codeMapping.put(code, t);
        }
        return codeMapping;
    }

    /**
     * 获取name->Enum的映射关系
     *
     * @param value
     * @return
     */
    private Map<String, T> getNameMap(Class<T> value) {
        Map<String, T> nameMapping = Maps.newHashMap();
        for (T t : Arrays.asList(value.getEnumConstants())) {
            nameMapping.put(t.name(), t);
        }
        return nameMapping;
    }


    /**
     * 根据code找到对应的枚举
     *
     * @param code
     * @return
     */
    public T codeOf(int code) {
        return (T) Preconditions.checkNotNull(codeMap.get(code), code + " is illegal.");
    }

    /**
     * 根据name找到对应的枚举
     *
     * @param name
     * @return
     */
    public T nameOf(String name) {
        return (T) Preconditions.checkNotNull(nameMap.get(name), name + " is illegal.");
    }
}
