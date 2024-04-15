

package com.keeay.anepoch.base.commons.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.util.Date;

/**
 * @author ying.pan
 * @date 2019/7/8 5:30 PM.
 */
public class ObjectMapperBuilder {
    private ObjectMapper objectMapper;

    /**
     * 私有构造函数
     */
    private ObjectMapperBuilder() {
        objectMapper = new ObjectMapper();
    }


    /**
     * 生成一个新的Builder
     *
     * @return
     */

    public static ObjectMapperBuilder newBuilder() {
        return new ObjectMapperBuilder();
    }

    /**
     * 使用参数生成一个新的ObjectMapper
     *
     * @return
     */
    public ObjectMapper build() {
        return objectMapper;
    }

    /**
     * 配置通用的全局选项
     *
     * @return
     */
    public ObjectMapperBuilder withDefaultOptions() {
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_MISSING_VALUES, true);
        objectMapper.configure(JsonParser.Feature.IGNORE_UNDEFINED, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return this;
    }

    /**
     * 配置了通用选项和时间选项
     *
     * @return
     */
    public ObjectMapperBuilder withDateTimeModule() {
        SimpleModule module = new SimpleModule("DateTimeModule", Version.unknownVersion());
        module.addSerializer(Date.class, new DateJsonSerializer());
        module.addDeserializer(Date.class, new DateJsonDeserializer());
        objectMapper.registerModule(module);
        return this;
    }
}
