/*
 *
 *  * Copyright (c) 2019. toxic.y-pan.com. All Rights Reserved.
 *
 */

package com.keeay.anepoch.base.commons.filter.cache;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * @author ying.pan
 * @date 2019/12/11 4:42 PM.
 */
public class CachedHttpServletResponseWrapper extends HttpServletResponseWrapper implements CachedStreamEntity {
    private CachedOutputStream cachedOutputStream;
    private PrintWriter printWriter;

    public CachedHttpServletResponseWrapper(HttpServletResponse cachedOutputStream, int initCacheSize, int maxCacheSize)
            throws IOException {
        super(cachedOutputStream);
        this.cachedOutputStream = new CachedOutputStream(cachedOutputStream.getOutputStream(), initCacheSize,
                maxCacheSize);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return cachedOutputStream;
    }

    @Override
    public CachedStream getCachedStream() {
        return cachedOutputStream;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if(printWriter == null){
            printWriter = new PrintWriter(new OutputStreamWriter(cachedOutputStream, getCharacterEncoding()));
        }
        return printWriter;
    }

    @Override
    public void flushStream() {
        if(printWriter != null){
            printWriter.flush();
        }
    }
}
