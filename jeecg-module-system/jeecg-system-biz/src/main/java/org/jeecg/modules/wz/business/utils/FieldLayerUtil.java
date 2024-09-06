package org.jeecg.modules.wz.business.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.design.core.tool.utils.StringUtil;
import org.jeecg.modules.wz.business.entity.ResourceLayer;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: -Circle
 * @Date: 2021/9/15 16:36
 * @Description:
 */
public class FieldLayerUtil {
    private static final String URL = "https://szlsservices.lucheng.gov.cn:8443/iserver/services/data-swdata/rest/data/datasources/luchengchengnao/datasets/{}/fields.json";
    private static final String URL_FORMAT = "{}/datasources/{}/datasets/{}/fields.json";

    /**
     * 返回接口处获取的字段列表
     * name -> 名字
     */
    public static Map<String, String> fieldsJson(ResourceLayer resourceLayer) {
        String url = StringUtil.format(URL, resourceLayer.getDataset());
        if (StringUtil.isNoneBlank(resourceLayer.getServiceAddress())) {
            url = StringUtil.format(URL_FORMAT, resourceLayer.getServiceAddress(), resourceLayer.getDatasource(), resourceLayer.getDataset());
        }
        Map<String, Object> param = new HashMap<>();
        param.put("returnAll", true);
        return getFieldsJson(url, param);
    }

    public static Map<String, String> fieldsJson(String address, String datasource, String dataset, String key) {
        Map<String, Object> param = new HashMap<>();
        param.put("returnAll", true);
        if (StrUtil.isNotBlank(key)) {
            param.put("key", key);
        }
        String url = StringUtil.format(URL_FORMAT, address, datasource, dataset);

        return getFieldsJson(url, param);
    }

    public static Map<String, String> getFieldsJson(String url, Map<String, Object> param) {
        Map<String, String> map = new HashMap<>();
        String result = HttpUtil.get(url, param);
        if (result.startsWith("[{")) {
            JSONArray array = JSON.parseArray(result);
            for (int i = 0; i < array.size(); i++) {
                JSONObject object = array.getJSONObject(i);
                map.put(object.getString("name"), object.getString("caption"));
            }
        }
        return map;
    }
}
