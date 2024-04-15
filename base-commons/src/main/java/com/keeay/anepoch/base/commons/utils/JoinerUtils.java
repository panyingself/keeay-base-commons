package com.keeay.anepoch.base.commons.utils;

import com.google.common.base.Joiner;

/**
 * Description:
 *
 * @author -  pany
 * Time - 2024/4/2 - 10:49
 */
public final class JoinerUtils {
    private JoinerUtils(){

    }

    /**
     * 逗号join
     */
    public static final Joiner JOINER_COMMA = Joiner.on(",");
    /**
     * 竖线join
     */
    public static final Joiner JOINER_VERTICAL_LINE = Joiner.on("|");
}
