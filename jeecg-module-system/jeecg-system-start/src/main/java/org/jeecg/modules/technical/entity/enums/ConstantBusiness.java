package org.jeecg.modules.technical.entity.enums;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.jeecg.modules.technical.service.FolderService.*;

/**
 * @author Yoko
 * @description 常量业务
 */
@AllArgsConstructor
@Slf4j
public enum ConstantBusiness {

    /**
     * 1.空的
     * 2.知识库
     */
    EMPTY("EMPTY", ""),
    KNOWLEDGE_BASE("KNOWLEDGE_BASE", "知识库");

    private final String id;
    private final String name;

    /**
     * @author Yoko
     * @date 2022/2/23 17:04
     * @param businessKey 业务key
     * @param idField id字段
     * @param nameField name字段
     * @param clazz 类
     * @description 返回指定类型的常量对象
     * @return T
     */
    @SneakyThrows
    public static <T> T getConstantBusiness(String businessKey, String idField, String nameField, Class<T> clazz) {
        ConstantBusiness business = ConstantBusiness.getEnum(businessKey);
        if (null == business) {
            return null;
        }
        T instance = clazz.newInstance();
        Method idSetter = clazz.getDeclaredMethod("set" +
                idField.replaceFirst(idField.charAt(0) + "", (idField.charAt(0) + "").toUpperCase()), String.class);
        Method nameSetter = clazz.getDeclaredMethod("set" +
                nameField.replaceFirst(nameField.charAt(0) + "", (nameField.charAt(0) + "").toUpperCase()), String.class);
        idSetter.invoke(instance, business.id);
        nameSetter.invoke(instance, business.name);
        return instance;
    }

    @SneakyThrows
    public static <T> T getConstantBusiness(String businessKey, List<String> idFields, List<String> nameFields, Class<T> clazz) {
        ConstantBusiness business = ConstantBusiness.getEnum(businessKey);
        if (null == business) {
            return null;
        }
        T instance = clazz.newInstance();
        // 封装字段
        IntStream.range(0, idFields.size())
                .forEach(idx -> {
                    String idField = idFields.get(idx);
                    String nameField = nameFields.get(idx);
                    try {
                        Method idSetter = clazz.getDeclaredMethod("set" +
                                idField.replaceFirst(idField.charAt(0) + "", (idField.charAt(0) + "").toUpperCase()), String.class);
                        Method nameSetter = clazz.getDeclaredMethod("set" +
                                nameField.replaceFirst(nameField.charAt(0) + "", (nameField.charAt(0) + "").toUpperCase()), String.class);
                        idSetter.invoke(instance, business.id);
                        nameSetter.invoke(instance, business.name);
                    } catch (Exception e) {
                        log.error("获取常量业务失败：{}", e.getMessage());
                    }
                });
        return instance;
    }

    @SneakyThrows
    public static <T> T getConstantBusiness(String businessKey, Class<T> clazz) {
        return getConstantBusiness(businessKey, Arrays.asList(BUSINESS_ID, PROJECT_ID), Arrays.asList(BUSINESS_NAME, PROJECT_NAME), clazz);
    }

    private static ConstantBusiness getEnum(String businessKey) {
        for (ConstantBusiness value : ConstantBusiness.values()) {
            if (value.id.equals(businessKey) || value.name.equals(businessKey)) {
                return value;
            }
        }
        return null;
    }

    public static ConstantBusiness of(String businessKey) {
        for (ConstantBusiness value : ConstantBusiness.values()) {
            if (value.id.equals(businessKey) || value.name.equals(businessKey)) {
                return value;
            }
        }
        return null;
    }

}
