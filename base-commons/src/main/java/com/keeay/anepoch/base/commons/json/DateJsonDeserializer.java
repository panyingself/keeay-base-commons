

package com.keeay.anepoch.base.commons.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

/**
 * @author ying.pan
 * @date 2019/7/8 5:31 PM.
 */
public class DateJsonDeserializer extends JsonDeserializer<Date> {

    public static final String DATE_FORMAT_PATTERN_WITH_TZ = "yyyy-MM-dd HH:mm:ssZ";
    public static final String DATE_FORMAT_PATTERN_NO_TZ = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_PATTERN_DATE = "yyyy-MM-dd";

    private String[] availableDateFormatPatterns;

    public DateJsonDeserializer() {
        availableDateFormatPatterns = new String[]{DATE_FORMAT_PATTERN_NO_TZ,
                DATE_FORMAT_PATTERN_WITH_TZ,
                DATE_FORMAT_PATTERN_DATE};
    }


    public DateJsonDeserializer(String[] availableDateFormatPatterns) {
        if (availableDateFormatPatterns == null || availableDateFormatPatterns.length == 0) {
            throw new IllegalArgumentException();
        }
        this.availableDateFormatPatterns = availableDateFormatPatterns;
    }

    @Override
    public Date deserialize(JsonParser jp, DeserializationContext context) throws IOException, JsonProcessingException {
        String date = jp.getText();
        if (StringUtils.isNoneBlank(date)) {
            try {
                return DateUtils.parseDate(date, availableDateFormatPatterns);
            } catch (ParseException e) {
                throw new JsonMappingException(jp, "cannot parse date string: " + date + jp.getCurrentLocation(), e);
            }
        }
        return null;
    }
}