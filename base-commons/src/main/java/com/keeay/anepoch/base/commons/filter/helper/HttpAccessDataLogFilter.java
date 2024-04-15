package com.keeay.anepoch.base.commons.filter.helper;

import com.keeay.anepoch.base.commons.filter.constants.TraceConstants;
import com.keeay.anepoch.base.commons.filter.TraceIdHelper;
import com.keeay.anepoch.base.commons.filter.cache.CachedHttpServletRequestWrapper;
import com.keeay.anepoch.base.commons.filter.cache.CachedHttpServletResponseWrapper;
import com.keeay.anepoch.base.commons.utils.RequestIpUtils;
import com.keeay.anepoch.base.commons.utils.UriMatcherUtils;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

public class HttpAccessDataLogFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger("HttpRequestAcceptAppender");

    /**
     * 最大缓存长度
     */
    private static final int MAX_CACHE_LEN = 2 * 1024 * 1024;
    /**
     * 初始化长度
     */
    private static final int INIT_CACHE_LEN = 512 * 1024;
    /**
     * 默认日志文件名称
     */
    private static final String DEFAULT_LOGGER_NAME = "accessDataLog";

    private String loggerName;
    /**
     * 哪些URL不进行处理
     */
    private String excludeUri;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String traceID = request.getHeader(TraceConstants.TRACE_ID);

        if (StringUtils.isBlank(traceID)) {
            traceID = TraceIdHelper.generate();
        }

        MDC.put(TraceConstants.TRACE_ID, traceID);

        //匹配当前请求的url是否需要被过滤 - 如果需要过滤则移除掉traceId
        if (UriMatcherUtils.match(excludeUri, request.getRequestURI())) {
            try {
                filterChain.doFilter(request, response);
            } finally {
                MDC.remove(TraceConstants.TRACE_ID);
            }
            return;
        }

        long start = System.currentTimeMillis();
        CachedHttpServletRequestWrapper httpServletRequestWrapper = new CachedHttpServletRequestWrapper(request,
                INIT_CACHE_LEN, MAX_CACHE_LEN);
        CachedHttpServletResponseWrapper httpServletResponseWrapper = new CachedHttpServletResponseWrapper(response,
                INIT_CACHE_LEN, MAX_CACHE_LEN);
        try {
            filterChain.doFilter(httpServletRequestWrapper, httpServletResponseWrapper);
        } finally {
            long end = System.currentTimeMillis();
            saveLogData(traceID, request, httpServletRequestWrapper, httpServletResponseWrapper, end - start);
            MDC.remove(TraceConstants.TRACE_ID);
        }
    }


    /**
     * @param request
     * @param httpServletRequestWrapper
     * @param httpServletResponseWrapper
     * @param time
     */
    private void saveLogData(String traceId, HttpServletRequest request, CachedHttpServletRequestWrapper httpServletRequestWrapper,
                             CachedHttpServletResponseWrapper httpServletResponseWrapper, long time) {
        try {
            // 如果使用了Writer就需要刷新流
            httpServletRequestWrapper.flushStream();
            httpServletResponseWrapper.flushStream();

            byte[] requestData = httpServletRequestWrapper.getCachedStream().getCached();
            byte[] responseData = httpServletResponseWrapper.getCachedStream().getCached();

            String requestString = requestData == null ? StringUtils.EMPTY : new String(requestData);
            String responseString = responseData == null ? StringUtils.EMPTY : new String(responseData);
            String uri = request.getRequestURI();

            // 注意这里返回的map不能更改，所以需要复制一份
            Map params = request.getParameterMap();
            params = Maps.newHashMap(params);

            // 处理请求参数map
            String paramString = StringUtils.EMPTY;
            List<String> pairs = Lists.newArrayList();
            if (MapUtils.isNotEmpty(params)) {
                for (Object name : params.keySet()) {
                    Object value = params.get(name);
                    if (value instanceof String) {
                        pairs.add(name + "=" + StringUtils.trimToEmpty((String) value));
                    } else if (value instanceof String[]) {
                        String[] values = (String[]) value;
                        for (String v : values) {
                            pairs.add(name + "=" + StringUtils.trimToEmpty(v));
                        }
                    } else if (value != null) {
                        pairs.add(name + "=" + value.toString());
                    }
                }
                paramString = Joiner.on("&").join(pairs);
            }

            if (StringUtils.equals(request.getContentType(), MediaType.APPLICATION_FORM_URLENCODED_VALUE)) {
                paramString = URLDecoder.decode(paramString, "UTF-8");
            }

            // 使用logger记录日志
            Logger logger = LoggerFactory.getLogger(StringUtils.isNotEmpty(loggerName) ? loggerName : DEFAULT_LOGGER_NAME);

            StringBuilder buffer = new StringBuilder();
            buffer.append("traceId=");
            buffer.append(traceId);
            buffer.append(", time=");
            buffer.append(time);
            buffer.append("ms");
            buffer.append(", uri=");
            buffer.append(request.getRequestURI());
//            buffer.append(", headers=");
//            buffer.append(addHeaders(request));
            buffer.append(", ip=");
            buffer.append(RequestIpUtils.getRealIP(httpServletRequestWrapper));
            buffer.append(", params=");
            buffer.append(paramString);
            buffer.append(", request=");
            buffer.append(requestString.replaceAll("\n|\r", ""));
            buffer.append(", response=");
            buffer.append(responseString.replaceAll("\n|\r", ""));
            logger.info(buffer.toString());
        } catch (Throwable e) {
            LOGGER.warn("log request data error", e);
        } finally {
            IOUtils.closeQuietly(httpServletRequestWrapper.getCachedStream());
            IOUtils.closeQuietly(httpServletResponseWrapper.getCachedStream());
        }
    }

    private String addHeaders(HttpServletRequest request) {
        Enumeration headerNames = request.getHeaderNames();
        StringBuilder builder = new StringBuilder("[");
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            builder.append(key).append(":").append(request.getHeader(key)).append(", ");
        }
        builder.append("]");
        return builder.toString();
    }

    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }

    public void setExcludeUri(String excludeUri) {
        this.excludeUri = excludeUri;
    }
}
