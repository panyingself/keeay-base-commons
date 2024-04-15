/*
 *
 *  * Copyright (c) 2019. toxic.y-pan.com. All Rights Reserved.
 *
 */

package com.keeay.anepoch.base.commons.filter.cache;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

/**
 * @author ying.pan
 * @date 2019/12/11 4:24 PM.
 */
public class CachedHttpServletRequestWrapper extends HttpServletRequestWrapper implements CachedStreamEntity {

    private CachedInputStream cachedInputStream;

    public CachedHttpServletRequestWrapper(HttpServletRequest httpServletRequest, int initCacheSize, int maxCacheSize)
            throws IOException {
        super(httpServletRequest);
        this.cachedInputStream = new CachedInputStream(httpServletRequest, initCacheSize, maxCacheSize);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return cachedInputStream;
    }

    @Override
    public CachedStream getCachedStream() {
        return cachedInputStream;
    }

    @Override
    public void flushStream() {
        //do nothing
    }
}
