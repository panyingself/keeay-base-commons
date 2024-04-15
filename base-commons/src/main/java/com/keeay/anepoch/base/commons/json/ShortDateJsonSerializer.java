

package com.keeay.anepoch.base.commons.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.keeay.anepoch.base.commons.base.data.ShortDate;

import java.io.IOException;

/**
 * @author ying.pan
 * @date 2019/7/8 5:38 PM.
 */
public class ShortDateJsonSerializer extends JsonSerializer<ShortDate> {
    @Override
    public void serialize(ShortDate shortDate, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(shortDate != null ? shortDate.toString() : "null");
    }
}
