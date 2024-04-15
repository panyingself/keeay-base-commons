/*
 *
 *  * Copyright (c) 2019. toxic.y-pan.com. All Rights Reserved.
 *
 */

package com.keeay.anepoch.base.commons.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author ying.pan
 * @date 2019/12/10 7:53 PM.
 */
public class UriMatcherUtils {
    /**
     * 判断一个URI是否是匹配一个模式
     * 支持多个URI，模式之间用,分割
     * 每个URI支持最后*号匹配
     *
     * @param patternStr 模式字符串
     * @param targetUri  待匹配的URI
     * @return 满足匹配true orElse false
     */
    public static boolean match(String patternStr, String targetUri) {
        if (StringUtils.isBlank(patternStr) || StringUtils.isBlank(targetUri)) {
            return false;
        }

        String[] matchUrls = StringUtils.split(patternStr, ",");
        if (matchUrls.length == 0) {
            return false;
        }

        for (String url : matchUrls) {
            url = StringUtils.trimToEmpty(url);
            if (url.endsWith("*")) {
                String sub = url.substring(0, url.length() - 1);
                if (targetUri.startsWith(sub)) {
                    return true;
                }
            } else {
                if (targetUri.equals(url)) {
                    return true;
                }
            }
        }

        return false;
    }
}
