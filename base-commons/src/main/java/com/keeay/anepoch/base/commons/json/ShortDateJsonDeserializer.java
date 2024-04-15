

package com.keeay.anepoch.base.commons.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.keeay.anepoch.base.commons.base.data.ShortDate;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * @author ying.pan
 * @date 2019/7/8 5:50 PM.
 */
public class ShortDateJsonDeserializer extends JsonDeserializer<ShortDate> {

    @Override
    public ShortDate deserialize(JsonParser jsonParser, DeserializationContext ctxt)
            throws IOException {
        String time = jsonParser.getText();
        return StringUtils.isBlank(time) ? null : ShortDate.valueOf(time);
    }
}
