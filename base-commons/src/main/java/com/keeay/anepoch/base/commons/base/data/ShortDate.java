


package com.keeay.anepoch.base.commons.base.data;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ying.pan
 * @date 2019/7/8 5:39 PM.
 */
public class ShortDate implements Serializable, Comparable<ShortDate> {

    private final static int BASE_YEAR = 1900;
    private final static char SEP = '-';
    private final static Joiner SEP_JOINER = Joiner.on(SEP);

    /**
     * 年月日表现形式
     * yyyyMMdd 的数字表现形式.
     */
    private int time;

    /**
     * 用于json序列化初始化, 严禁调用.
     */
    @Deprecated
    public ShortDate() {
    }

    /***
     * 使用yyyy-MM-dd格式字符串初始化
     *
     * @param str
     */
    public ShortDate(String str) {
        Preconditions.checkArgument(StringUtils.isNoneBlank(str), "Null String");
        Preconditions.checkArgument(StringUtils.length(str) == 10,
                "ShortDate format must be yyyy-MM-dd: " + str);

        int year = NumberUtils.toInt(str.substring(0, 4));
        int month = NumberUtils.toInt(str.substring(5, 7));
        int day = NumberUtils.toInt(str.substring(8, 10));
        init(year, month, day);
    }

    /***
     * 使用日期对象初始化 注意这里使用了默认时区，如果非默认时区，请调用年月日显示初始化
     *
     * @param date
     */
    public ShortDate(Date date) {
        int year = date.getYear() + BASE_YEAR;
        int month = date.getMonth() + 1;
        int day = date.getDate();
        init(year, month, day);
    }

    /***
     * 使用年月日初始化
     *
     * @param year
     * @param month
     * @param day
     */
    public ShortDate(int year, int month, int day) {
        init(year, month, day);
    }

    private void init(int year, int month, int day) {
        check(year, 0, 9999, "year is invalid");
        check(month, 1, 12, "month is invalid");
        check(day, 1, 31, "day is invalid");

        time = year;
        time <<= 6;
        time |= month;
        time <<= 6;
        time |= day;
    }

    /**
     * @return 返回年份
     */
    public int getYear() {
        return (time >> 12) & 0x3FFF;
    }

    /**
     * @return 返回月份
     */
    public int getMonth() {
        return (time >> 6) & 0x3F;
    }

    /***
     * @return 返回天数
     */
    public int getDay() {
        return time & 0x3F;
    }

    /**
     * 使用默认时区转换为日期对象 时分秒部分默认为00:00:00.000
     *
     * @return
     */
    public Date toDate() {
        return new Date(getYear() - BASE_YEAR, getMonth() - 1, getDay());
    }

    @Override
    public int hashCode() {
        return time;
    }

    @Override
    public boolean equals(Object obj) {
        return null != obj && (this == obj || (getClass() == obj.getClass()
                && compareTo((ShortDate) obj) == NumberUtils.INTEGER_ZERO));

    }

    @Override
    public String toString() {
        return SEP_JOINER.join(fill(getYear(), 4), fill(getMonth(), 2), fill(getDay(), 2));
    }

    private static String fill(int i, int length) {
        return StringUtils.leftPad(String.valueOf(i), length, NumberUtils.INTEGER_ZERO.toString());
    }

    public boolean after(ShortDate o) {
        return compareTo(o) == NumberUtils.INTEGER_ONE;
    }

    public boolean before(ShortDate o) {
        return compareTo(o) == NumberUtils.INTEGER_MINUS_ONE;
    }

    @Override
    public int compareTo(ShortDate o) {
        return NumberUtils.compare(toLiteral(), o.toLiteral());
    }

    public static ShortDate valueOf(String value) {
        return new ShortDate(value);
    }

    public static ShortDate valueOf(Date date) {
        return new ShortDate(date);
    }

    private int toLiteral() {
        return getYear() * 10000 + getMonth() * 100 + getDay();
    }

    private static void check(int value, int min, int max, String msg) {
        if (value < min || value > max) {
            throw new IllegalArgumentException(String.format("%s: value=%d min=%d max=%d", msg, value, min, max));
        }
    }

}