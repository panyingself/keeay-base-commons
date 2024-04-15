

package com.keeay.anepoch.base.commons.base.data;

import com.google.common.base.Preconditions;
import com.google.common.collect.Range;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * @author ying.pan
 * @date 2019/7/8 5:42 PM.
 */
public class TimeRange implements Serializable {

    private ShortTime fromTime;

    private ShortTime toTime;

    protected TimeRange() {
    }

    public TimeRange(ShortTime fromTime, ShortTime toTime) {
        this.fromTime = fromTime;
        this.toTime = toTime;
    }

    public ShortTime getFromTime() {
        return fromTime;
    }

    public TimeRange setFromTime(ShortTime fromTime) {
        this.fromTime = fromTime;
        return this;
    }

    public ShortTime getToTime() {
        return toTime;
    }

    public TimeRange setToTime(ShortTime toTime) {
        this.toTime = toTime;
        return this;
    }

    public static TimeRange of(ShortTime fromTime, ShortTime toTime) {
        return new TimeRange(fromTime, toTime);
    }

    public Range<ShortTime> toRange() {
        Preconditions.checkNotNull(fromTime, "fromTime is null");
        Preconditions.checkNotNull(toTime, "toTime is null");
        return Range.closed(fromTime, toTime);
    }

    public boolean contains(ShortTime theOne) {
        return toRange().contains(theOne);
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o, true);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(17, 37, this);
    }

}
