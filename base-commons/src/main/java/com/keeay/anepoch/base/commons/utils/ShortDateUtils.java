

package com.keeay.anepoch.base.commons.utils;

import com.keeay.anepoch.base.commons.base.data.DateRange;
import com.keeay.anepoch.base.commons.base.data.ShortDate;
import com.keeay.anepoch.base.commons.base.data.Weekday;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author ying.pan
 * @date 2019/7/8 8:16 PM.
 */
public class ShortDateUtils {
    public static ShortDate addDays(ShortDate shortDate, int delta) {
        return addUnit(shortDate, delta, Calendar.DAY_OF_YEAR);
    }

    public static ShortDate addMonths(ShortDate shortDate, int delta) {
        return addUnit(shortDate, delta, Calendar.MONTH);
    }

    public static ShortDate addYears(ShortDate shortDate, int delta) {
        return addUnit(shortDate, delta, Calendar.YEAR);
    }

    private static ShortDate addUnit(ShortDate shortDate, int delta, int unit) {
        Date date = shortDate.toDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(unit, delta);
        return new ShortDate(cal.getTime());
    }

    public static int diffDays(ShortDate from, ShortDate to) {
        long time1 = from.toDate().getTime();
        long time2 = to.toDate().getTime();
        long span = time2 - time1;
        return (int) (span / TimeUnit.DAYS.toMillis(NumberUtils.INTEGER_ONE));
    }

    public static Weekday getWeekDay(ShortDate shortDate) {
        return Weekday.weekOf(shortDate.toDate());
    }

    public static Set<Weekday> mapToWeekdays(ShortDate fromDate, ShortDate toDate) {
        return mapToEffectiveDays(fromDate, toDate)
                .stream()
                .map(ShortDateUtils::getWeekDay)
                .collect(Collectors.toSet());
    }

    public static List<ShortDate> mapToEffectiveDays(ShortDate fromDate, ShortDate toDate) {
        List<ShortDate> effectiveList = Lists.newArrayList();
        if (fromDate.after(toDate)) {
            return effectiveList;
        }
        for (ShortDate date = fromDate; !date.after(toDate); date = addDays(date, NumberUtils.INTEGER_ONE)) {
            effectiveList.add(date);
        }
        return effectiveList;
    }

    public static List<ShortDate> mapToEffectiveDays(DateRange dateRange) {
        if (Objects.isNull(dateRange)) {
            return Collections.emptyList();
        }

        return mapToEffectiveDays(dateRange.getFromDate(), dateRange.getToDate());
    }

    public static boolean isValidShortDate(String shortDate) {
        try {
            new ShortDate(shortDate);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static ShortDate findFirstDate(List<ShortDate> shortDateList) {
        if (CollectionUtils.isEmpty(shortDateList)) {
            return null;
        }
        Collections.sort(shortDateList);
        return shortDateList.get(NumberUtils.INTEGER_ZERO);
    }

    /**
     * 将日期队列合并成一个段段连续的时间段。
     * 如
     *
     * @param iter
     * @return
     */
    public static List<DateRange> mergeAdjacentItems(Iterable<ShortDate> iter) {
        if (Objects.isNull(iter) || Iterables.isEmpty(iter)) {
            return Collections.emptyList();
        }
        List<DateRange> ranges = Lists.newArrayList();
        List<ShortDate> sorted = Ordering.natural().sortedCopy(iter);
        Iterator<ShortDate> iterator = sorted.iterator();
        DateRange dateRange = DateRange.single(iterator.next());
        for (ShortDate date : sorted) {
            if (isContinuous(dateRange.getToDate(), date)) {
                dateRange.setToDate(date);
            } else {
                ranges.add(dateRange);
                dateRange = DateRange.single(date);
            }
        }
        ranges.add(dateRange);
        return ranges;
    }

    /**
     * 判断两个日期是否为相邻的
     *
     * @param left
     * @param right
     * @return
     */
    public static boolean isContinuous(ShortDate left, ShortDate right) {
        if (null == left || null == right) {
            return false;
        }
        ShortDate max = Ordering.natural().max(left, right);
        ShortDate min = Ordering.natural().min(left, right);
        return max.equals(min) || ShortDateUtils.diffDays(min, max) == NumberUtils.INTEGER_ONE;
    }
}
