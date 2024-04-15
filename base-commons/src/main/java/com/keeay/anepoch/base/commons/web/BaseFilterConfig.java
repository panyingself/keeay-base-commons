package com.keeay.anepoch.base.commons.web;

import com.keeay.anepoch.base.commons.filter.helper.HttpAccessDataLogFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BaseFilterConfig {
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        // 这里可以使用 new，也可以在 Filter 上加 @Component 注入进来
        bean.setFilter(new HttpAccessDataLogFilter());
        bean.addUrlPatterns("/*");
        bean.setName("loggerFilter");
        // 值越小，优先级越高
        bean.setOrder(-999);
        return bean;
    }
}
