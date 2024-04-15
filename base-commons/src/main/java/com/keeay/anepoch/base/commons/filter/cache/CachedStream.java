/*
 *
 *  * Copyright (c) 2019. toxic.y-pan.com. All Rights Reserved.
 *
 */

package com.keeay.anepoch.base.commons.filter.cache;

import java.io.Closeable;

/**
 * @author ying.pan
 * @date 2019/12/11 4:26 PM.
 */
public interface CachedStream extends Closeable {
    /**
     * 返回缓存的字节数据
     *
     * @return
     */
    byte[] getCached();
}
