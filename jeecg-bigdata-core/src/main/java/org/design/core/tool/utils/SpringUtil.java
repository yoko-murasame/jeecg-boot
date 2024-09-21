package org.design.core.tool.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/utils/SpringUtil.class */
public class SpringUtil implements ApplicationContextAware {
    private static final Logger log = LoggerFactory.getLogger(SpringUtil.class);
    private static ApplicationContext context;

    public void setApplicationContext(ApplicationContext context2) throws BeansException {
        context = context2;
    }

    public static <T> T getBean(Class<T> clazz) {
        if (clazz == null) {
            return null;
        }
        return (T) context.getBean(clazz);
    }

    public static <T> T getBean(String beanId) {
        if (beanId == null) {
            return null;
        }
        return (T) context.getBean(beanId);
    }

    public static <T> T getBean(String beanName, Class<T> clazz) {
        if (null == beanName || StringPool.EMPTY.equals(beanName.trim()) || clazz == null) {
            return null;
        }
        return (T) context.getBean(beanName, clazz);
    }

    public static ApplicationContext getContext() {
        if (context == null) {
            return null;
        }
        return context;
    }

    public static void publishEvent(ApplicationEvent event) {
        if (context != null) {
            try {
                context.publishEvent(event);
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
        }
    }
}
