

package com.keeay.anepoch.base.commons.base.data;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.lang.Integer.parseInt;

/**
 * 一天中的某个时间点
 * 无时区
 * 可以表达[00:00:00 - 47:59:59] 时间区间内的任意时间点
 * 为了提高效率，使用18个位来存储
 * 20[unused] + 6[hours]  + 6[minutes] + 6[seconds]
 * @author ying.pan
 * @date 2019/7/8 5:40 PM.
 */
public class ShortTime implements Serializable, Comparable<ShortTime> {

    private final static char COLON_SP = ':';
    private final static Splitter COLON_SPLITTER = Splitter.on(COLON_SP).trimResults().omitEmptyStrings();
    private final static Joiner COLON_JOINER = Joiner.on(COLON_SP).skipNulls();
    private final static int FIXED_LENGTH = 2;
    private final static Splitter FIXED_LENGTH_SPLITTER = Splitter.fixedLength(FIXED_LENGTH)
            .trimResults()
            .omitEmptyStrings();
    private final static int TIMES_SIZE = 3;
    private final static int HH_MM_SIZE = 4;

    private int bits;

    /**
     * 用于json序列化初始化, 严禁调用.
     */
    @Deprecated
    public ShortTime() {
    }

    /**
     * 使用hh:mm:ss格式的字符串初始化
     *
     * @param time
     */
    public ShortTime(String time) {
        Preconditions.checkNotNull(time, "Time String is illegal!");
        List<String> times = COLON_SPLITTER.splitToList(time);

        if (CollectionUtils.size(times) != TIMES_SIZE) {
            throw new IllegalArgumentException("time string pattern should be hh:mm:ss");
        }
        init(parseInt(times.get(0)), parseInt(times.get(1)), parseInt(times.get(2)));
    }

    /***
     * 通过给定格式解析time初始化
     *
     * @param time   日期字符串
     * @param format 日期格式
     * @throws ParseException
     */
    public ShortTime(String time, String format) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat(format);
        Date parsedDate = df.parse(time);
        Calendar cal = Calendar.getInstance();
        cal.setTime(parsedDate);
        init(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
    }

    /**
     * 使用具体的时分秒毫秒构建
     *
     * @param hours
     * @param minutes
     * @param seconds
     */
    public ShortTime(int hours, int minutes, int seconds) {
        init(hours, minutes, seconds);
    }

    /**
     * 使用时分秒初始化对象
     *
     * @param hours
     * @param minutes
     * @param seconds
     */
    private void init(int hours, int minutes, int seconds) {
        check(hours, 0, 47, "hours is invalid");
        check(minutes, 0, 59, "minutes is invalid");
        check(seconds, 0, 59, "seconds is invalid");

        bits = 0;
        bits |= hours;
        bits <<= 6;
        bits |= minutes;
        bits <<= 6;
        bits |= seconds;
    }

    /**
     * @return 返回小时数，如果为第二天以上，则返回值大于24
     */
    public int getHour() {
        return (bits >> 12) & 0x3F;
    }

    /**
     * @return 返回分钟数
     */
    public int getMinute() {
        return (bits >> 6) & 0x3F;
    }

    /**
     * @return 返回秒
     */
    public int getSecond() {
        return (bits >> 0) & 0x3F;
    }

    /**
     * 设置小时
     *
     * @param hour
     */
    @Deprecated
    public void setHour(int hour) {
        check(hour, 0, 47, "hour is invalid");
        //clear bits..
        bits &= ~(0x3F << 12);
        bits |= hour << 12;
    }

    /**
     * 设置分钟
     *
     * @param minute
     */
    @Deprecated
    public void setMinute(int minute) {
        check(minute, 0, 59, "minute is invalid");
        bits &= ~(0x3F << 6);
        bits |= minute << 6;
    }

    /**
     * 设置秒数
     *
     * @param second
     * @return
     */
    @Deprecated
    public void setSecond(int second) {
        check(second, 0, 59, "second is invalid");
        bits &= ~(0x3F);
        bits |= second;
    }


    @Override
    public int hashCode() {
        return bits;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ShortTime other = (ShortTime) obj;
        if (bits != other.bits) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d:%02d", getHour(), getMinute(), getSecond());
    }

    @Override
    public int compareTo(ShortTime o) {
        return NumberUtils.compare(toSeconds(), o.toSeconds());
    }

    public boolean after(ShortTime o) {
        return compareTo(o) == NumberUtils.INTEGER_ONE;
    }

    public boolean before(ShortTime o) {
        return compareTo(o) == NumberUtils.INTEGER_MINUS_ONE;
    }

    public static ShortTime valueOf(String value) {
        return new ShortTime(value);
    }

    public static ShortTime parseHHmm(String val) {
        if (StringUtils.length(val) != HH_MM_SIZE) {
            throw new IllegalArgumentException("illegal param of parse shortTime");
        }
        val = val + StringUtils.repeat(NumberUtils.INTEGER_ZERO.toString(), FIXED_LENGTH);
        return new ShortTime(COLON_JOINER.join(FIXED_LENGTH_SPLITTER.split(val)));
    }

    public static ShortTime valueOf(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return new ShortTime(
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                cal.get(Calendar.SECOND));
    }

    public static ShortTime valueOfSeconds(int seconds) {
        int h = seconds / 3600;
        seconds -= h * 3600;
        int m = seconds / 60;
        seconds -= m * 60;
        int s = seconds;
        return new ShortTime(h, m, s);
    }

    private static void check(int value, int min, int max, String msg) {
        if (value < min || value > max) {
            throw new IllegalArgumentException(String.format("%s: value=%d min=%d max=%d", msg, value, min, max));
        }
    }

    public int toSeconds() {
        int s = (bits >> 0) & 0x3F;
        int m = (bits >> 6) & 0x3F;
        int h = (bits >> 12) & 0x3F;
        return h * 3600 + m * 60 + s;
    }
}
