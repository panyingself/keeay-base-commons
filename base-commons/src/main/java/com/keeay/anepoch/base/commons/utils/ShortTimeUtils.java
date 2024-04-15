

package com.keeay.anepoch.base.commons.utils;


import com.keeay.anepoch.base.commons.base.data.ShortTime;

import java.util.concurrent.TimeUnit;

/**
 * @author ying.pan
 * @date 2019/7/8 8:16 PM.
 */
public class ShortTimeUtils {
    public static ShortTime addHours(ShortTime shortTime, int delta) {
        int seconds = shortTime.toSeconds();
        return ShortTime.valueOfSeconds(seconds + (int) TimeUnit.HOURS.toSeconds(delta));
    }

    public static ShortTime addMinutes(ShortTime shortTime, int delta) {
        int seconds = shortTime.toSeconds();
        return ShortTime.valueOfSeconds(seconds + (int) TimeUnit.MINUTES.toSeconds(delta));
    }

    public static ShortTime addSeconds(ShortTime shortTime, int delta) {
        int seconds = shortTime.toSeconds();
        return ShortTime.valueOfSeconds(seconds + delta);
    }

    public static int diffSeconds(ShortTime from, ShortTime to) {
        return to.toSeconds() - from.toSeconds();
    }

    public static int diffHours(ShortTime from, ShortTime to) {
        return (int) TimeUnit.SECONDS.toHours(diffSeconds(from, to));
    }
}
