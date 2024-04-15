

package com.keeay.anepoch.base.commons.base.data;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.SetUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ying.pan
 * @date 2019/7/8 5:46 PM.
 */
public class WeekdaySet implements Serializable {

    private static final byte ALL_WEEKDAYS = (byte) 0x7f;

    private Set<Weekday> weekdays;

    public WeekdaySet() {
    }

    public WeekdaySet(Set<Weekday> weekdays) {
        this.weekdays = weekdays;
    }

    public WeekdaySet setWeekdays(Set<Weekday> weekdays) {
        this.weekdays = weekdays;
        return this;
    }

    public Set<Weekday> getWeekdays() {
        return weekdays;
    }

    /**
     * 创建WeekdaySet对象
     *
     * @param weekdays
     * @return
     */
    public static WeekdaySet create(Weekday... weekdays) {
        Preconditions.checkNotNull(weekdays, "weekdays is null.");
        return create(Arrays.asList(weekdays));
    }

    /**
     * 创建一个WeekdaySet对象
     *
     * @param weekdays
     * @return
     */
    public static WeekdaySet create(Collection<Weekday> weekdays) {
        Preconditions.checkNotNull(weekdays, "Weekdays is null.");
        return new WeekdaySet(weekdays.stream().collect(Collectors.toSet()));
    }

    /**
     * 将int值转换成对象
     *
     * @param value
     * @return
     */
    public static WeekdaySet create(byte value) {
        if (value == ALL_WEEKDAYS) {
            return allWeekdays();
        }

        Set<Weekday> weekdays = Sets.newHashSet();
        Arrays.stream(Weekday.values())
                .filter(weekday -> BooleanUtils.toBoolean(value & (NumberUtils.BYTE_ONE << weekday.getCode())))
                .forEach(weekdays::add);

        return WeekdaySet.create(weekdays);
    }

    public static WeekdaySet allWeekdays() {
        return create(Weekday.values());
    }

    /**
     * 按照位存储Weekdays
     */
    public byte toByte() {
        if (CollectionUtils.size(getWeekdays()) == Weekday.values().length) {
            return ALL_WEEKDAYS;
        }

        byte value = NumberUtils.BYTE_ZERO;
        for (Weekday weekday : SetUtils.emptyIfNull(getWeekdays())) {
            value |= NumberUtils.BYTE_ONE << weekday.getCode();
        }

        return value;
    }
}
