package com.qianxunclub.ticket.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author zhangbin
 * @date 2019-05-30 17:37
 * @description: TODO
 */
@Component
public class ApplicationContextHelper implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    public ApplicationContextHelper() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHelper.applicationContext = applicationContext;
    }

    public static Object getBean(String beanName) {
        return applicationContext != null ? applicationContext.getBean(beanName) : null;
    }

    public static <T> T getBean(Class<T> t) {
        return applicationContext != null ? applicationContext.getBean(t) : null;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
