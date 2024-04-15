/*
 *
 *  * Copyright (c) 2019. toxic.y-pan.com. All Rights Reserved.
 *
 */

package com.keeay.anepoch.base.commons.filter.cache;

/**
 * @author ying.pan
 * @date 2019/12/11 4:25 PM.
 */
public interface CachedStreamEntity {
    /**
     * 获取cache的stream
     *
     * @return
     */
    CachedStream getCachedStream();

    /**
     * 刷出
     */
    void flushStream();
}
