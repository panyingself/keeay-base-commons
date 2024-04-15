

package com.keeay.anepoch.base.commons.base.data;

import com.keeay.anepoch.base.commons.base.helper.EnumHelper;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

/**
 * @author ying.pan
 * @date 2019/7/8 5:43 PM.
 */
public enum Weekday implements Serializable {
    /**
     * 周日
     */
    SUNDAY(0),
    /**
     * 周一
     */
    MONDAY(1),
    /**
     * 周二
     */
    TUESDAY(2),
    /**
     * 周三
     */
    WEDNESDAY(3),
    /**
     * 周四
     */
    THURSDAY(4),
    /**
     * 周五
     */
    FRIDAY(5),
    /**
     * 周六
     */
    SATURDAY(6);

    private final int code;

    Weekday(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return reflectionToString(this, SHORT_PREFIX_STYLE);
    }

    private static EnumHelper enumHelper = EnumHelper.of(Weekday.class)
            .initCodeMap()
            .initNameMap();

    public static Weekday codeOf(int code) {
        return (Weekday) enumHelper.codeOf(code);
    }

    public static Weekday nameOf(String name) {
        return (Weekday) enumHelper.nameOf(name);
    }

    public static Weekday weekOf(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return codeOf((cal.get((Calendar.DAY_OF_WEEK)) - NumberUtils.INTEGER_ONE) % Weekday.values().length);
    }
}
