package com.keeay.anepoch.base.commons.web;

import com.keeay.anepoch.base.commons.base.result.HttpResult;
import com.keeay.anepoch.base.commons.base.result.HttpResultFilterIgnore;
import com.keeay.anepoch.base.commons.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class HttpResultWrapper implements ResponseBodyAdvice {
    @ExceptionHandler(BizException.class)
    @ResponseBody
    public HttpResult handleTException(HttpServletRequest request, BizException ex) {
        log.error("process url {} failed", request.getRequestURL().toString(), ex);
        return HttpResult.failure(ex.getErrorCode().getCode(), ex.getMessage());
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof HttpResult) {
            return body;
        }
        if (body instanceof HttpResultFilterIgnore) {
            return body;
        }
        return HttpResult.success(body);
    }

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return this.conditionCheck(returnType, converterType);
    }

    private boolean conditionCheck(MethodParameter returnType, Class converterType) {
        //对于返回类型为HttpResult.class的数据不进行处理
        if (returnType.getParameterType() == HttpResult.class) {
            return false;
        }
        //对于返回类型为String.class的数据不进行处理
        if (returnType.getParameterType() == String.class) {
            return false;
        }

        //对于返回类型为Result的名称数据不进行处理
        if ("Result".equals(returnType.getParameterType().getSimpleName())) {
            return false;
        }
        //对于swagger的请求不予处理
        if (returnType.getDeclaringClass().getName().contains("springfox")) {
            return false;
        }
        //对于.js的请求不予处理
        if (returnType.getDeclaringClass().getName().contains(".js")) {
            return false;
        }
        //对于.css的请求不予处理
        if (returnType.getDeclaringClass().getName().contains(".css")) {
            return false;
        }
        return true;
    }


}
