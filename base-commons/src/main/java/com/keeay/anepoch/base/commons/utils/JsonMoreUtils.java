

package com.keeay.anepoch.base.commons.utils;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.MapType;
import com.keeay.anepoch.base.commons.base.data.ShortDate;
import com.keeay.anepoch.base.commons.base.data.ShortTime;
import com.google.common.base.Strings;
import com.keeay.anepoch.base.commons.json.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * @author ying.pan
 * @date 2019/7/8 5:23 PM.
 */
public class JsonMoreUtils {
    private static ObjectMapper objectMapper = null;

    static {
        objectMapper = ObjectMapperBuilder.newBuilder()
                .withDefaultOptions()
                .build();
        addMoreModules(objectMapper);
    }

    private static void addMoreModules(ObjectMapper objectMapper) {
        SimpleModule module = new SimpleModule("DateTimeModule", Version.unknownVersion());
        module.addSerializer(Date.class, new DateJsonSerializer());
        module.addSerializer(ShortDate.class, new ShortDateJsonSerializer());
        module.addSerializer(ShortTime.class, new ShortTimeJsonSerializer());
        module.addSerializer(LocalDateTime.class, new LocalDateTimeJsonSerializer());
        module.addSerializer(LocalDate.class, new LocalDateJsonSerializer());
        module.addSerializer(LocalTime.class, new LocalTimeJsonSerializer());
        module.addSerializer(Long.class, new LongJsonSerializer());

        module.addDeserializer(Date.class, new DateJsonDeserializer());
        module.addDeserializer(ShortDate.class, new ShortDateJsonDeserializer());
        module.addDeserializer(ShortTime.class, new ShortTimeJsonDeserializer());
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeJsonDeserializer());
        module.addDeserializer(LocalDate.class, new LocalDateJsonDeserializer());
        module.addDeserializer(LocalTime.class, new LocalTimeJsonDeserializer());
        module.addDeserializer(Long.class, new LongJsonDeserializer());
        objectMapper.registerModule(module);
    }

    public static ObjectMapper getGlobalObjectMapper() {
        return objectMapper;
    }


    /**
     * 把对象转为json字符串
     *
     * @param obj 要转换的对象 可以为null
     * @return
     */
    public static String toJson(Object obj) {
        try {
            return getGlobalObjectMapper().writeValueAsString(obj);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 把json字符串转为对象
     *
     * @param json 要转换的字符串
     * @param cls  目标实例类名
     * @param <T>
     * @return
     */

    public static <T> T toBean(String json, Class<T> cls) {
        try {
            return (T) getGlobalObjectMapper().readValue(json, cls);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 把json字符串转为对象，给定类型信息
     *
     * @param json          要装换的字符串
     * @param typeReference 类型引用
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T toBean(String json, TypeReference<T> typeReference) {
        try {
            return (T) getGlobalObjectMapper().readValue(json, typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> ofList(String json, Class<T> tClass) {
        if (Strings.isNullOrEmpty(json)) {
            return null;
        } else {
            JavaType javaType = getGlobalObjectMapper().getTypeFactory().constructParametrizedType(ArrayList.class, ArrayList.class, new Class[]{tClass});
            try {
                return (List) objectMapper.readValue(json, javaType);
            } catch (IOException var4) {
                throw new RuntimeException(var4);
            }
        }
    }

    public static <K, V> Map<K, V> ofMap(String json, Class<K> keyClass, Class<V> valueClass) {
        if (Strings.isNullOrEmpty(json)) {
            return null;
        } else {
            MapType mapType = getGlobalObjectMapper().getTypeFactory().constructMapType(HashMap.class, keyClass, valueClass);

            try {
                return (Map) objectMapper.readValue(json, mapType);
            } catch (IOException var5) {
                throw new RuntimeException(var5);
            }
        }
    }
}
