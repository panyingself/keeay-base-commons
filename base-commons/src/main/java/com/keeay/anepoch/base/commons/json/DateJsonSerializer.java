

package com.keeay.anepoch.base.commons.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.lang3.time.FastDateFormat;

import java.io.IOException;
import java.util.Date;

/**
 * @author ying.pan
 * @date 2019/7/8 5:33 PM.
 */
public class DateJsonSerializer extends JsonSerializer<Date> {
    private FastDateFormat dateFormat;

    public DateJsonSerializer() {
        dateFormat = FastDateFormat.getInstance(DateJsonDeserializer.DATE_FORMAT_PATTERN_WITH_TZ);
    }

    public DateJsonSerializer(String dateFormatPattern) {
        this.dateFormat = FastDateFormat.getInstance(dateFormatPattern);
    }

    @Override
    public void serialize(Date date, JsonGenerator jg, SerializerProvider serializerProvider)
            throws IOException, JsonProcessingException {
        jg.writeString(date != null ? dateFormat.format(date) : "null");
    }
}
