/*
 *
 *  * Copyright (c) 2019. toxic.y-pan.com. All Rights Reserved.
 *
 */

package com.keeay.anepoch.base.commons.filter.cache;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author ying.pan
 * @date 2019/12/11 4:44 PM.
 */
public class CachedOutputStream extends ServletOutputStream implements CachedStream {
    private ByteArrayOutputStream cachedOutputStream;
    private ServletOutputStream srcOutputStream;
    private int maxCacheSize;

    public CachedOutputStream(ServletOutputStream srcOutputStream, int initCacheSize, int maxCacheSize) {
        CachedStreamUtils.checkCacheSizeParam(initCacheSize, maxCacheSize);
        this.srcOutputStream = srcOutputStream;
        this.cachedOutputStream = new ByteArrayOutputStream(initCacheSize);
        this.maxCacheSize = maxCacheSize;
    }

    @Override
    public byte[] getCached() {
        return cachedOutputStream.toByteArray();
    }

    @Override
    public void write(int b) throws IOException {
        this.srcOutputStream.write(b);
        if (this.cachedOutputStream.size() < maxCacheSize) {
            CachedStreamUtils.safeWrite(cachedOutputStream, b);
        }
    }

    @Override
    public void close() throws IOException {
        super.close();
        this.cachedOutputStream.close();
    }

    @Override
    public boolean isReady() {
        return this.srcOutputStream.isReady();
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {
        this.srcOutputStream.setWriteListener(writeListener);
    }
}
