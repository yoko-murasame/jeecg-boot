package org.design.core.tool.utils;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.design.core.tool.support.BaseBeanCopier;
import org.design.core.tool.support.BeanProperty;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.cglib.beans.BeanGenerator;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.cglib.core.CodeGenerationException;
import org.springframework.util.Assert;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/utils/BeanUtil.class */
public class BeanUtil extends BeanUtils {
    public static <T> T newInstance(Class<?> clazz) {
        return (T) instantiateClass(clazz);
    }

    public static <T> T newInstance(String clazzStr) {
        try {
            return (T) newInstance(Class.forName(clazzStr));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object getProperty(Object bean, String propertyName) {
        Assert.notNull(bean, "bean Could not null");
        return BeanMap.create(bean).get(propertyName);
    }

    public static void setProperty(Object bean, String propertyName, Object value) {
        Assert.notNull(bean, "bean Could not null");
        BeanMap.create(bean).put(propertyName, value);
    }

    public static <T> T clone(T source) {
        return (T) copy((Object) source, (Class<Object>) source.getClass());
    }

    public static <T> T copy(Object source, Class<T> clazz) {
        BaseBeanCopier copier = BaseBeanCopier.create(source.getClass(), clazz, false);
        T to = (T) newInstance((Class<?>) clazz);
        copier.copy(source, to, null);
        return to;
    }

    public static void copy(Object source, Object targetBean) {
        BaseBeanCopier.create(source.getClass(), targetBean.getClass(), false).copy(source, targetBean, null);
    }

    public static <T> T copyProperties(Object source, Class<T> target) throws BeansException {
        T to = (T) newInstance((Class<?>) target);
        copyProperties(source, to);
        return to;
    }

    public static Map<String, Object> toMap(Object bean) {
        return BeanMap.create(bean);
    }

    public static <T> T toBean(Map<String, Object> beanMap, Class<T> valueType) {
        T bean = (T) newInstance((Class<?>) valueType);
        BeanMap.create(bean).putAll(beanMap);
        return bean;
    }

    public static Object generator(Object superBean, BeanProperty... props) {
        Object genBean = generator(superBean.getClass(), props);
        copy(superBean, genBean);
        return genBean;
    }

    public static Object generator(Class<?> superclass, BeanProperty... props) {
        BeanGenerator generator = new BeanGenerator();
        generator.setSuperclass(superclass);
        generator.setUseCache(true);
        for (BeanProperty prop : props) {
            generator.addProperty(prop.getName(), prop.getType());
        }
        return generator.create();
    }

    public static PropertyDescriptor[] getBeanGetters(Class type) {
        return getPropertiesHelper(type, true, false);
    }

    public static PropertyDescriptor[] getBeanSetters(Class type) {
        return getPropertiesHelper(type, false, true);
    }

    private static PropertyDescriptor[] getPropertiesHelper(Class type, boolean read, boolean write) {
        try {
            PropertyDescriptor[] all = getPropertyDescriptors(type);
            if (read && write) {
                return all;
            }
            List<PropertyDescriptor> properties = new ArrayList<>(all.length);
            for (PropertyDescriptor pd : all) {
                if (read && pd.getReadMethod() != null) {
                    properties.add(pd);
                } else if (write && pd.getWriteMethod() != null) {
                    properties.add(pd);
                }
            }
            return (PropertyDescriptor[]) properties.toArray(new PropertyDescriptor[0]);
        } catch (BeansException ex) {
            throw new CodeGenerationException(ex);
        }
    }
}
