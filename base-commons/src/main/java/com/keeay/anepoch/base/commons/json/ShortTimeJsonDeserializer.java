

package com.keeay.anepoch.base.commons.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.keeay.anepoch.base.commons.base.data.ShortTime;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * @author ying.pan
 * @date 2019/7/8 5:47 PM.
 */
public class ShortTimeJsonDeserializer extends JsonDeserializer<ShortTime> {
    @Override
    public ShortTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String text = p.getText();
        return StringUtils.isNotBlank(text) ? ShortTime.valueOf(text) : null;
    }
}
