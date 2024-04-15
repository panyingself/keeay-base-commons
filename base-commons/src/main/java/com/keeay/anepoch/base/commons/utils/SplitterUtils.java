package com.keeay.anepoch.base.commons.utils;

import com.google.common.base.Splitter;

/**
 * Description:
 *
 * @author -  pany
 * Time - 2024/4/2 - 10:49
 */
public final class SplitterUtils {
    private SplitterUtils(){

    }

    /**
     * 逗号拆分
     */
    public static final Splitter SPLITTER_COMMA = Splitter.on(",").trimResults();
    /**
     * 竖线拆分
     */
    public static final Splitter SPLITTER_VERTICAL_LINE = Splitter.on("|").trimResults();
}
