

package com.keeay.anepoch.base.commons.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.keeay.anepoch.base.commons.base.data.ShortTime;

import java.io.IOException;

/**
 * @author ying.pan
 * @date 2019/7/8 5:49 PM.
 */
public class ShortTimeJsonSerializer extends JsonSerializer<ShortTime> {
    @Override
    public void serialize(ShortTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(null != value ? value.toString() : "null");
    }
}
