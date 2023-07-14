package org.jeecg.modules.workflow.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.aspect.DictAspect;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.common.util.oConvertUtils;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;

/**
 * 积木报表工具类
 *
 * @author Yoko
 * @since 2022/12/28 11:01
 */
@Slf4j
public class YokoJimuUtil {

    /**
     * 处理报表打印的自定义API结果（单个）
     *
     * @param t 实体
     * @param customDictHandler 自定义字段处理
     * @return com.alibaba.fastjson.JSONObject
     * @author Yoko
     * @since 2022/12/28 11:04
     */
    public static <T> JSONObject toReportResult(T t, Consumer<JSONObject> customDictHandler) {
        Assert.notNull(t, "实例不能为空，最低限度需提供一个空实体");
        long start = System.currentTimeMillis();
        ObjectMapper mapper = new ObjectMapper();
        String json = "{}";
        try {
            // 解决@JsonFormat注解解析不了的问题详见SysAnnouncement类的@JsonFormat
            json = mapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            log.error("json解析失败" + e.getMessage(), e);
        }
        JSONObject item = JSONObject.parseObject(json);
        DictAspect dictAspect = SpringContextUtils.getBean(DictAspect.class);
        for (Field field : oConvertUtils.getAllFields(t)) {
            // update-end--Author:scott  -- Date:20190603 ----for：解决继承实体字段无法翻译问题------
            if (field.getAnnotation(Dict.class) != null) {
                String code = field.getAnnotation(Dict.class).dicCode();
                String text = field.getAnnotation(Dict.class).dicText();
                String table = field.getAnnotation(Dict.class).dictTable();
                String key = String.valueOf(item.get(field.getName()));

                // 翻译字典值对应的txt
                String textValue = dictAspect.translateDictValue(code, text, table, key, false);

                log.debug(" 字典Val : " + textValue);
                log.debug(" __翻译字典字段__ " + field.getName() + CommonConstant.DICT_TEXT_SUFFIX + "： " + textValue);
                item.put(field.getName() + CommonConstant.DICT_TEXT_SUFFIX, textValue);
            }
            // date类型默认转换string格式化日期
            if ("java.util.Date".equals(field.getType().getName()) && field.getAnnotation(JsonFormat.class) == null && item.get(field.getName()) != null) {
                SimpleDateFormat aDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                item.put(field.getName(), aDate.format(new Date((Long) item.get(field.getName()))));
            }
        }

        // 添加测试字段
        // item.put("testpicture", "https://oss.epc-pm.cn/chengfa/20211203/%E4%BA%94%E4%BB%91%E5%A4%B4%E5%98%89%E8%8B%91_1638500910884.JPG");
        // 处理自定义字典
        customDictHandler.accept(item);

        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(item);
        result.put("data", jsonArray);

        long end = System.currentTimeMillis();
        log.debug("通过id查询报表数据 耗时: " + (end - start) + "ms");
        return result;
    }
}
