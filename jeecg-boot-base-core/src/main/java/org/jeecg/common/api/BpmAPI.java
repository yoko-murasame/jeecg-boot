package org.jeecg.common.api;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.system.vo.SysOnlListDataModel;
import org.jeecg.common.system.vo.SysOnlListQueryModel;

import java.util.Map;

/**
 * 流程、Online相关API
 */
public interface BpmAPI {

    /**
     * 获取Online列表数据
     *
     * @author Yoko
     * @since 2024/9/20 12:25
     * @param code 表单编码
     * @param queryParam 查询参数，需要分页请传入：pageSize、pageNo
     * @return org.jeecg.common.system.vo.SysOnlListDataModel
     */
    SysOnlListDataModel getDataByCode(String code, Map<String, Object> queryParam);

    /**
     * 获取Online列表数据
     *
     * @author Yoko
     * @since 2024/9/20 12:25
     * @param onlListQueryModel 查询实体
     * @return org.jeecg.common.system.vo.SysOnlListDataModel
     */
    SysOnlListDataModel getData(SysOnlListQueryModel onlListQueryModel);

    /**
     * 新增Online表单数据
     *
     * @author Yoko
     * @param code 表单编码
     * @param javaBean JavaBean实体
     * @return java.lang.String 数据库表名
     */
    String saveManyFormDataByJavaBean(String code, Object javaBean) throws Exception;

    /**
     * 新增Online表单数据
     *
     * @author Yoko
     * @param code 表单编码
     * @param formData 表单数据
     * @return java.lang.String 数据库表名
     */
    String saveManyFormData(String code, JSONObject formData) throws Exception;

    /**
     * 编辑Online表单数据
     *
     * @author Yoko
     * @param code 表单编码
     * @param javaBean JavaBean实体
     * @return java.lang.String 数据库表名
     */
    String editManyFormDataByJavaBean(String code, Object javaBean) throws Exception;

    /**
     * 编辑Online表单数据
     * FIXME 请注意Date类型的数据在JSONObject中会被转换成Long的timestamp，更新sql需要做兼容性改造
     * FIXME 目前PostgreSQL已兼容Long类型的时间戳保存
     *
     * @author Yoko
     * @param code 表单编码
     * @param formData 表单数据，务必注意驼峰转换成蛇形，或者调用editManyFormDataByJavaBean方法
     * @return java.lang.String 数据库表名
     */
    String editManyFormData(String code, JSONObject formData) throws Exception;

}
