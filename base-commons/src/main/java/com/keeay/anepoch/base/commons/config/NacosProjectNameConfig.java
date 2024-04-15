package com.keeay.anepoch.base.commons.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Description:
 *
 * @author -  pany
 * Time - 2024/2/23 - 14:06
 */
@Configuration
public class NacosProjectNameConfig implements EnvironmentAware {
    @Value("${spring.application.name}")
    private String applicationName;

    @Override
    public void setEnvironment(Environment environment) {
        if (StringUtils.isBlank(System.getProperty("project.name"))){
            System.setProperty("project.name", applicationName);
        }
    }
}
