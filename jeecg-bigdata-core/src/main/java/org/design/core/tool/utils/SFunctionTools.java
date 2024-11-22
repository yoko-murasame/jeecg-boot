package org.design.core.tool.utils;

import com.baomidou.mybatisplus.core.toolkit.*;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * SFunction工具类
 */
public class SFunctionTools {

    /**
     * 可序列化
     */
    private static final int FLAG_SERIALIZABLE = 1;

    private static Map<String, SFunction> functionMap = new HashMap<>();

    /**
     * 获取方法的sfunction
     * @param entityClass 实体类
     * @param fieldName 字段名
     * @return sfunction
     */
    public static SFunction getSFunction(Class<?> entityClass, String fieldName) {
        if (functionMap.containsKey(entityClass.getName() + fieldName)) {
            return functionMap.get(entityClass.getName() + fieldName);
        }
        Field field = getDeclaredField(entityClass, fieldName);
        if(field == null){
            throw ExceptionUtils.mpe("This class %s is not have field %s ", entityClass.getName(), fieldName);
        }
        SFunction func = null;
        final MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType methodType = MethodType.methodType(field.getType(), entityClass);
        final CallSite site;
        String getFunName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        try {
            site = LambdaMetafactory.altMetafactory(lookup,
                    "invoke",
                    MethodType.methodType(SFunction.class),
                    methodType,
                    lookup.findVirtual(entityClass, getFunName, MethodType.methodType(field.getType())),
                    methodType, FLAG_SERIALIZABLE);
            func = (SFunction) site.getTarget().invokeExact();
            functionMap.put(entityClass.getName() + field, func);
            return func;
        } catch (Throwable e) {
            throw ExceptionUtils.mpe("This class %s is not have method %s ", entityClass.getName(), getFunName);
        }
    }

    /**
     * 获取字段
     * @param clazz 类
     * @param fieldName 字段名
     * @return 字段
     */
    public static Field getDeclaredField(Class<?> clazz, String fieldName) {
        Field field = null;

        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                return field;
            } catch (Exception e) {
                // 这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
                // 如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了

            }
        }

        return null;
    }

}
