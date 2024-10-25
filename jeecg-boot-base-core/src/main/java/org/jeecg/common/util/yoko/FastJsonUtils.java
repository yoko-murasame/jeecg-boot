package org.jeecg.common.util.yoko;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.NameFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.Map;

/**
 * FastJson工具类
 *
 * @author Yoko
 * @since 2024/10/25 16:10
 */
public class FastJsonUtils {

    // FastJSON-数据转换成蛇形配置
    public static final NameFilter SnakeCaseFilter = (object, name, value) -> {
        // 排除分页参数
        if ("pageSize".equals(name) || "pageNo".equals(name)) {
            return name;
        }
        if ("page_size".equals(name)) {
            return "pageSize";
        }
        if ("page_no".equals(name)) {
            return "pageNo";
        }
        // 其他字段 SnakeCase 转换
        return PropertyNamingStrategy.SnakeCase.translate(name);
    };

    /**
     * FastJSON-数据转换成蛇形
     *
     * @author Yoko
     * @since 2024/10/25 16:10
     * @param object
     * @param type
     * @param features
     * @return T
     */
    public static <T> T toSnakeMap(Object object, TypeReference<T> type, Feature... features) {
        String text = toSnakeJson(object);
        return JSON.parseObject(text, type, features);
    }

    public static <T> T toSnakeMap(Object object, TypeReference<T> type, SerializerFeature[] serializerFeatures, Feature[] features) {
        String text = toSnakeJson(object, serializerFeatures);
        return JSON.parseObject(text, type, features);
    }

    /**
     * FastJSON-数据转换成蛇形
     *
     * @author Yoko
     * @since 2024/10/25 16:10
     * @param object
     * @return java.util.Map<java.lang.String, java.lang.Object>
     */
    public static Map<String, Object> toSnakeMap(Object object, SerializerFeature... features) {
        String text = toSnakeJson(object, features);
        return JSON.parseObject(text, new TypeReference<Map<String, Object>>(){});
    }

    public static Map<String, Object> toSnakeMapUseDateFormat(Object object) {
        String text = toSnakeJson(object, SerializerFeature.WriteDateUseDateFormat);
        return JSON.parseObject(text, new TypeReference<Map<String, Object>>(){});
    }

    public static JSONObject toSnakeJsonObject(Object object, SerializerFeature... features) {
        String text = toSnakeJson(object, features);
        return JSON.parseObject(text);
    }

    /**
     * FastJSON-对象转换成蛇形JSON字符串
     *
     * @author Yoko
     * @since 2024/10/25 16:10
     * @param object
     * @return java.lang.String
     */
    public static String toSnakeJson(Object object, SerializerFeature... features) {
        return JSON.toJSONString(object, SnakeCaseFilter, features);
    }

}
