


package com.keeay.anepoch.base.commons.base.data;

import com.google.common.base.Preconditions;
import com.google.common.collect.Range;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

/**
 * @author ying.pan
 * @date 2019/7/8 5:41 PM.
 */
public class DateRange implements Serializable, Comparable<DateRange> {

    /**
     * 开始时间
     */
    private ShortDate fromDate;

    /**
     * 结束时间
     */
    private ShortDate toDate;

    public DateRange() {
    }

    /**
     * [fromDate, toDate]
     *
     * @param fromDate
     * @param toDate
     */
    public DateRange(ShortDate fromDate, ShortDate toDate) {
        this();
        setFromDate(fromDate);
        setToDate(toDate);
    }

    public ShortDate getFromDate() {
        return fromDate;
    }

    public DateRange setFromDate(ShortDate fromDate) {
        this.fromDate = fromDate;
        checkShortDate(fromDate, getToDate(), "Invalid fromDate: ");
        return this;
    }

    private void checkShortDate(ShortDate thisDay, ShortDate otherDay, String errorMsg) {
        Preconditions.checkNotNull(thisDay, errorMsg + thisDay);
        if (null != otherDay) {
            Preconditions.checkArgument(!this.fromDate.after(this.toDate),
                    "Invalid fromDate : " + fromDate + " , and toDate : " + toDate);
        }
    }

    public ShortDate getToDate() {
        return toDate;
    }

    public DateRange setToDate(ShortDate toDate) {
        this.toDate = toDate;
        checkShortDate(toDate, getFromDate(), "Invalid toDate: ");
        return this;
    }

    @Override
    public String toString() {
        return reflectionToString(this, SHORT_PREFIX_STYLE);
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o, true);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(17, 37, this);
    }

    public Range<ShortDate> toRange() {
        Preconditions.checkNotNull(fromDate, "fromDate is null");
        Preconditions.checkNotNull(toDate, "toDate is null");
        return Range.closed(fromDate, toDate);
    }

    public boolean encloses(DateRange dateRange) {
        return toRange().encloses(dateRange.toRange());
    }

    public boolean contains(ShortDate day) {
        return toRange().contains(day);
    }

    public static DateRange of(ShortDate fromDate, ShortDate toDate) {
        return new DateRange(fromDate, toDate);
    }

    public static DateRange single(ShortDate date) {
        return of(date, date);
    }

    @Override
    public int compareTo(DateRange o) {
        int value = getFromDate().compareTo(o.getFromDate());
        return value == 0 ? getToDate().compareTo(o.getToDate()) : value;
    }
}
