package org.jeecg.common.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.jeecg.common.api.CommonAPI;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoTransDict;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecg.common.constant.CacheConstant;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.constant.enums.TransDictType;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.common.util.oConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Description: 字典aop类
 * @Author: dangzhenghui
 * @Date: 2019-3-17 21:50
 * @Version: 1.0
 */
@Aspect
@Component
@Slf4j
@ConditionalOnMissingClass("cn.com.hyit.config.DictAspect")
public class DictAspect {

    @Lazy
    @Autowired
    private CommonAPI commonApi;

    // @20240905 bug fix 这里出现key乱码，发现是redisTemplate问题，换成redisUtil就好了
    // @Autowired
    // public RedisTemplate redisTemplate;

    @Resource
    private RedisUtil redisUtil;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String JAVA_UTIL_DATE = "java.util.Date";

    /**
     * 定义切点Pointcut
     */
    @Pointcut("execution(public * org.jeecg.modules..*.*Controller.*(..)) || @annotation(org.jeecg.common.aspect.annotation.AutoDict)")
    public void excudeService() {
    }

    @Pointcut("@annotation(org.jeecg.common.aspect.annotation.AutoTransDict)")
    public void autoTransPoint() {
    }

    @Around("excudeService()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long time1 = System.currentTimeMillis();
        Object result = pjp.proceed();
        long time2 = System.currentTimeMillis();
        log.debug("获取JSON数据 耗时：" + (time2 - time1) + "ms");
        long start = System.currentTimeMillis();
        result = this.parseDictText(result);
        long end = System.currentTimeMillis();
        log.debug("注入字典到JSON数据  耗时" + (end - start) + "ms");
        return result;
    }

    @Around("autoTransPoint()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long time1=System.currentTimeMillis();
        Object result = point.proceed();
        long time2=System.currentTimeMillis();
        log.debug("获取JSON数据 耗时："+(time2-time1)+"ms");
        long start=System.currentTimeMillis();
        this.parseDictFromAnnotation(point, result);
        long end=System.currentTimeMillis();
        log.debug("解析注入JSON数据  耗时"+(end-start)+"ms");
        return result;
    }

    /**
     * 本方法针对返回对象为Result 的IPage的分页列表数据进行动态字典注入
     * 字典注入实现 通过对实体类添加注解@dict 来标识需要的字典内容,字典分为单字典code即可 ，table字典 code table text配合使用与原来jeecg的用法相同
     * 示例为SysUser   字段为sex 添加了注解@Dict(dicCode = "sex") 会在字典服务立马查出来对应的text 然后在请求list的时候将这个字典text，已字段名称加_dictText形式返回到前端
     * 例输入当前返回值的就会多出一个sex_dictText字段
     * {
     * sex:1,
     * sex_dictText:"男"
     * }
     * 前端直接取值sext_dictText在table里面无需再进行前端的字典转换了
     * customRender:function (text) {
     * if(text==1){
     * return "男";
     * }else if(text==2){
     * return "女";
     * }else{
     * return text;
     * }
     * }
     * 目前vue是这么进行字典渲染到table上的多了就很麻烦了 这个直接在服务端渲染完成前端可以直接用
     *
     * @param result
     */
    private Object parseDictText(Object result) {
        if (result instanceof Result) {
            if (((Result) result).getResult() instanceof IPage) {
                List<JSONObject> items = new ArrayList<>();

                // step.1 筛选出加了 Dict 注解的字段列表
                List<Field> dictFieldList = new ArrayList<>();
                // 字典数据列表， key = 字典code，value=数据列表
                Map<String, List<String>> dataListMap = new HashMap<>(5);
                // 取出结果集
                List<Object> records = ((IPage) ((Result) result).getResult()).getRecords();
                // update-begin--Author:zyf -- Date:20220606 ----for：【VUEN-1230】 判断是否含有字典注解,没有注解返回-----
                Boolean hasDict = checkHasDict(records);
                if (!hasDict) {
                    return result;
                }

                log.debug(" __ 进入字典翻译切面 DictAspect —— ");
                // update-end--Author:zyf -- Date:20220606 ----for：【VUEN-1230】 判断是否含有字典注解,没有注解返回-----
                for (Object record : records) {
                    String json = "{}";
                    try {
                        // update-begin--Author:zyf -- Date:20220531 ----for：【issues/#3629】 DictAspect Jackson序列化报错-----
                        // 解决@JsonFormat注解解析不了的问题详见SysAnnouncement类的@JsonFormat
                        json = objectMapper.writeValueAsString(record);
                        // update-end--Author:zyf -- Date:20220531 ----for：【issues/#3629】 DictAspect Jackson序列化报错-----
                    } catch (JsonProcessingException e) {
                        log.error("json解析失败" + e.getMessage(), e);
                    }
                    // update-begin--Author:scott -- Date:20211223 ----for：【issues/3303】restcontroller返回json数据后key顺序错乱 -----
                    JSONObject item = JSONObject.parseObject(json, Feature.OrderedField);
                    // update-end--Author:scott -- Date:20211223 ----for：【issues/3303】restcontroller返回json数据后key顺序错乱 -----

                    // update-begin--Author:scott -- Date:20190603 ----for：解决继承实体字段无法翻译问题------
                    // for (Field field : record.getClass().getDeclaredFields()) {
                    // 遍历所有字段，把字典Code取出来，放到 map 里
                    for (Field field : oConvertUtils.getAllFields(record)) {
                        String value = item.getString(field.getName());
                        if (oConvertUtils.isEmpty(value)) {
                            continue;
                        }
                        // update-end--Author:scott  -- Date:20190603 ----for：解决继承实体字段无法翻译问题------
                        if (field.getAnnotation(Dict.class) != null) {
                            if (!dictFieldList.contains(field)) {
                                dictFieldList.add(field);
                            }
                            String code = field.getAnnotation(Dict.class).dicCode();
                            String text = field.getAnnotation(Dict.class).dicText();
                            String table = field.getAnnotation(Dict.class).dictTable();

                            List<String> dataList;
                            String dictCode = code;
                            if (!StringUtils.isEmpty(table)) {
                                dictCode = String.format("%s,%s,%s", table, text, code);
                            }
                            dataList = dataListMap.computeIfAbsent(dictCode, k -> new ArrayList<>());
                            this.listAddAllDeduplicate(dataList, Arrays.asList(value.split(",")));
                        }
                        // date类型默认转换string格式化日期
                        // update-begin--Author:zyf -- Date:20220531 ----for：【issues/#3629】 DictAspect Jackson序列化报错-----
                        // if (JAVA_UTIL_DATE.equals(field.getType().getName())&&field.getAnnotation(JsonFormat.class)==null&&item.get(field.getName())!=null){
                        // SimpleDateFormat aDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        // item.put(field.getName(), aDate.format(new Date((Long) item.get(field.getName()))));
                        //}
                        // update-end--Author:zyf -- Date:20220531 ----for：【issues/#3629】 DictAspect Jackson序列化报错-----
                    }
                    items.add(item);
                }

                // step.2 调用翻译方法，一次性翻译
                Map<String, List<DictModel>> translText = this.translateAllDict(dataListMap);

                // step.3 将翻译结果填充到返回结果里
                for (JSONObject record : items) {
                    for (Field field : dictFieldList) {
                        String code = field.getAnnotation(Dict.class).dicCode();
                        String text = field.getAnnotation(Dict.class).dicText();
                        String table = field.getAnnotation(Dict.class).dictTable();

                        String fieldDictCode = code;
                        if (!StringUtils.isEmpty(table)) {
                            fieldDictCode = String.format("%s,%s,%s", table, text, code);
                        }

                        String value = record.getString(field.getName());
                        if (oConvertUtils.isNotEmpty(value)) {
                            List<DictModel> dictModels = translText.get(fieldDictCode);
                            if (dictModels == null || dictModels.size() == 0) {
                                continue;
                            }

                            String textValue = this.translDictText(dictModels, value);
                            log.debug(" 字典Val : " + textValue);
                            log.debug(" __翻译字典字段__ " + field.getName() + CommonConstant.DICT_TEXT_SUFFIX + "： " + textValue);

                            // TODO-sun 测试输出，待删
                            log.debug(" ---- dictCode: " + fieldDictCode);
                            log.debug(" ---- value: " + value);
                            log.debug(" ----- text: " + textValue);
                            log.debug(" ---- dictModels: " + JSON.toJSONString(dictModels));

                            record.put(field.getName() + CommonConstant.DICT_TEXT_SUFFIX, textValue);
                        }
                    }
                }

                ((IPage) ((Result) result).getResult()).setRecords(items);
            }

        }
        return result;
    }

    /**
     * list 去重添加
     */
    private void listAddAllDeduplicate(List<String> dataList, List<String> addList) {
        // 筛选出dataList中没有的数据
        List<String> filterList = addList.stream().filter(i -> !dataList.contains(i)).collect(Collectors.toList());
        dataList.addAll(filterList);
    }

    /**
     * 一次性把所有的字典都翻译了
     * 1.  所有的普通数据字典的所有数据只执行一次SQL
     * 2.  表字典相同的所有数据只执行一次SQL
     *
     * @param dataListMap
     * @return
     */
    public Map<String, List<DictModel>> translateAllDict(Map<String, List<String>> dataListMap) {
        // 翻译后的字典文本，key=dictCode
        Map<String, List<DictModel>> translText = new HashMap<>(5);
        // 需要翻译的数据（有些可以从redis缓存中获取，就不走数据库查询）
        List<String> needTranslData = new ArrayList<>();
        // step.1 先通过redis中获取缓存字典数据
        for (String dictCode : dataListMap.keySet()) {
            List<String> dataList = dataListMap.get(dictCode);
            if (dataList.isEmpty()) {
                continue;
            }
            // 表字典需要翻译的数据
            List<String> needTranslDataTable = new ArrayList<>();
            for (String s : dataList) {
                String data = s.trim();
                if (data.isEmpty()) {
                    continue; // 跳过循环
                }
                if (dictCode.contains(",")) {
                    String keyString = String.format(CacheConstant.SYS_DICT_TABLE_CACHE + ":SimpleKey [%s,%s]", dictCode, data);
                    if (redisUtil.hasKey(keyString)) {
                        try {
                            String text = oConvertUtils.getString(redisUtil.get(keyString));
                            List<DictModel> list = translText.computeIfAbsent(dictCode, k -> new ArrayList<>());
                            list.add(new DictModel(data, text));
                        } catch (Exception e) {
                            log.warn(e.getMessage());
                        }
                    } else if (!needTranslDataTable.contains(data)) {
                        // 去重添加
                        needTranslDataTable.add(data);
                    }
                } else {
                    String keyString = String.format(CacheConstant.SYS_DICT_CACHE + ":%s:%s", dictCode, data);
                    if (redisUtil.hasKey(keyString)) {
                        try {
                            String text = oConvertUtils.getString(redisUtil.get(keyString));
                            List<DictModel> list = translText.computeIfAbsent(dictCode, k -> new ArrayList<>());
                            list.add(new DictModel(data, text));
                        } catch (Exception e) {
                            log.warn(e.getMessage());
                        }
                    } else if (!needTranslData.contains(data)) {
                        // 去重添加
                        needTranslData.add(data);
                    }
                }

            }
            // step.2 调用数据库翻译表字典
            if (!needTranslDataTable.isEmpty()) {
                String[] arr = dictCode.split(",");
                String table = arr[0], text = arr[1], code = arr[2];
                String values = String.join(",", needTranslDataTable);
                log.debug("translateDictFromTableByKeys.dictCode:" + dictCode);
                log.debug("translateDictFromTableByKeys.values:" + values);
                List<DictModel> texts = commonApi.translateDictFromTableByKeys(table, text, code, values);
                log.debug("translateDictFromTableByKeys.result:" + texts);
                List<DictModel> list = translText.computeIfAbsent(dictCode, k -> new ArrayList<>());
                list.addAll(texts);
                // 做 redis 缓存
                for (DictModel dict : texts) {
                    String redisKey = String.format(CacheConstant.SYS_DICT_TABLE_CACHE + ":SimpleKey [%s,%s]", dictCode, dict.getValue());
                    try {
                        // update-begin-author:taoyan date:20211012 for: 字典表翻译注解缓存未更新 issues/3061
                        // 保留5分钟
                        redisUtil.set(redisKey, dict.getText(), 300);
                        // update-end-author:taoyan date:20211012 for: 字典表翻译注解缓存未更新 issues/3061
                    } catch (Exception e) {
                        log.warn(e.getMessage(), e);
                    }
                }
            }
        }

        // step.3 调用数据库进行翻译普通字典
        if (!needTranslData.isEmpty()) {
            List<String> dictCodeList = Arrays.asList(dataListMap.keySet().toArray(new String[]{}));
            // 将不包含逗号的字典code筛选出来，因为带逗号的是表字典，而不是普通的数据字典
            List<String> filterDictCodes = dictCodeList.stream().filter(key -> !key.contains(",")).collect(Collectors.toList());
            String dictCodes = String.join(",", filterDictCodes);
            String values = String.join(",", needTranslData);
            log.debug("translateManyDict.dictCodes:" + dictCodes);
            log.debug("translateManyDict.values:" + values);
            Map<String, List<DictModel>> manyDict = commonApi.translateManyDict(dictCodes, values);
            log.debug("translateManyDict.result:" + manyDict);
            for (String dictCode : manyDict.keySet()) {
                List<DictModel> list = translText.computeIfAbsent(dictCode, k -> new ArrayList<>());
                List<DictModel> newList = manyDict.get(dictCode);
                list.addAll(newList);

                // 做 redis 缓存，@20240905 bug fix 这里出现key乱码，发现是redisTemplate问题，换成redisUtil就好了
                for (DictModel dict : newList) {
                    String redisKey = String.format(CacheConstant.SYS_DICT_CACHE + ":%s:%s", dictCode, dict.getValue());
                    try {
                        redisUtil.set(redisKey, dict.getText());
                    } catch (Exception e) {
                        log.warn(e.getMessage(), e);
                    }
                }
            }
        }
        return translText;
    }

    /**
     * 字典值替换文本
     *
     * @param dictModels
     * @param values
     * @return
     */
    public String translDictText(List<DictModel> dictModels, String values) {
        List<String> result = new ArrayList<>();

        // 允许多个逗号分隔，允许传数组对象
        String[] splitVal = values.split(",");
        for (String val : splitVal) {
            String dictText = val;
            for (DictModel dict : dictModels) {
                if (val.equals(dict.getValue())) {
                    dictText = dict.getText();
                    break;
                }
            }
            result.add(dictText);
        }
        return String.join(",", result);
    }

    /**
     * 翻译字典文本
     *
     * @param code
     * @param text
     * @param table
     * @param key
     * @return
     */
    @Deprecated
    public String translateDictValue(String code, String text, String table, String key, boolean reverse) {
        if (oConvertUtils.isEmpty(key)) {
            return null;
        }
        StringBuffer textValue = new StringBuffer();
        String[] keys = key.split(",");
        for (String k : keys) {
            String tmpValue = null;
            log.debug(" 字典 key : " + k);
            if (k.trim().length() == 0) {
                continue; // 跳过循环
            }
            // update-begin--Author:scott -- Date:20210531 ----for： !56 优化微服务应用下存在表字段需要字典翻译时加载缓慢问题-----
            if (!StringUtils.isEmpty(table)) {
                log.debug("--DictAspect------dicTable=" + table + " ,dicText= " + text + " ,dicCode=" + code);
                String keyString = String.format(CacheConstant.SYS_DICT_TABLE_CACHE + ":SimpleKey [%s,%s,%s,%s]", table, text, code, k.trim());
                if (redisUtil.hasKey(keyString)) {
                    try {
                        tmpValue = oConvertUtils.getString(redisUtil.get(keyString));
                    } catch (Exception e) {
                        log.warn(e.getMessage());
                    }
                } else {
                    if (reverse) {
                        tmpValue = commonApi.translateDictFromTable(table, code, text, k.trim());
                    } else {
                        tmpValue = commonApi.translateDictFromTable(table, text, code, k.trim());
                    }
                }
            } else {
                String keyString = String.format(CacheConstant.SYS_DICT_CACHE + ":%s:%s", code, k.trim());
                if (redisUtil.hasKey(keyString)) {
                    try {
                        tmpValue = oConvertUtils.getString(redisUtil.get(keyString));
                    } catch (Exception e) {
                        log.warn(e.getMessage());
                    }
                } else {
                    if (reverse) {
                        tmpValue = commonApi.translateDictReverse(code, k.trim());
                    } else {
                        tmpValue = commonApi.translateDict(code, k.trim());
                    }
                }
            }
            // update-end--Author:scott -- Date:20210531 ----for： !56 优化微服务应用下存在表字段需要字典翻译时加载缓慢问题-----

            if (tmpValue != null) {
                if (!"".equals(textValue.toString())) {
                    textValue.append(",");
                }
                textValue.append(tmpValue);
            }

        }
        return textValue.toString();
    }

    /**
     * 检测返回结果集中是否包含Dict注解
     *
     * @param records
     * @return
     */
    private Boolean checkHasDict(List<Object> records) {
        if (oConvertUtils.isNotEmpty(records) && records.size() > 0) {
            for (Field field : oConvertUtils.getAllFields(records.get(0))) {
                if (oConvertUtils.isNotEmpty(field.getAnnotation(Dict.class))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 自动翻译 AutoTransDict 注解的字典内容
     *
     * @author Yoko
     * @since 2023/4/28 16:54
     * @param joinPoint
     * @param result
     * @return void
     */
    private void parseDictFromAnnotation(ProceedingJoinPoint joinPoint, Object result) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        AutoTransDict autoTransDict = method.getAnnotation(AutoTransDict.class);

        // 手动翻译类型为 Result<对象> 的字典
        if (TransDictType.OBJECT.equals(autoTransDict.type())) {
            Object record = ((Result) result).getResult();
            // 排除字符串和基础类型
            if (null == record || record instanceof String || this.isPrimitiveOrWrapper(record.getClass())) {
                return;
            }
            ObjectMapper mapper = new ObjectMapper();
            String json="{}";
            try {
                //解决@JsonFormat注解解析不了的问题详见SysAnnouncement类的@JsonFormat
                json = mapper.writeValueAsString(record);
            } catch (JsonProcessingException e) {
                log.error("json解析失败"+e.getMessage(),e);
            }
            JSONObject item = JSONObject.parseObject(json);
            //update-begin--Author:scott -- Date:20190603 ----for：解决继承实体字段无法翻译问题------
            //for (Field field : record.getClass().getDeclaredFields()) {
            for (Field field : oConvertUtils.getAllFields(record)) {
                //update-end--Author:scott  -- Date:20190603 ----for：解决继承实体字段无法翻译问题------
                if (field.getAnnotation(Dict.class) != null) {
                    String code = field.getAnnotation(Dict.class).dicCode();
                    String text = field.getAnnotation(Dict.class).dicText();
                    String table = field.getAnnotation(Dict.class).dictTable();
                    String key = String.valueOf(item.get(field.getName()));

                    //翻译字典值对应的txt
                    String textValue = translateDictValue(code, text, table, key, false);

                    log.debug(" 字典Val : "+ textValue);
                    log.debug(" __翻译字典字段__ "+field.getName() + CommonConstant.DICT_TEXT_SUFFIX+"： "+ textValue);
                    item.put(field.getName() + CommonConstant.DICT_TEXT_SUFFIX, textValue);

                    // 从 字典文本 -> 字典key
                    if (autoTransDict.reverseValue()) {
                        //翻译字典文本对应的key
                        String textValue2 = translateDictValue(code, text, table, key, true);
                        log.debug(" 字典Val 反转 : "+ textValue2);
                        log.debug(" __翻译字典字段 反转__ "+field.getName() + CommonConstant.DICT_KEY_SUFFIX+"： "+ textValue2);
                        item.put(field.getName() + CommonConstant.DICT_KEY_SUFFIX, textValue2);
                    }
                }
                //date类型默认转换string格式化日期
                if (field.getType().getName().equals("java.util.Date")&&field.getAnnotation(JsonFormat.class)==null&&item.get(field.getName())!=null){
                    SimpleDateFormat aDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    item.put(field.getName(), aDate.format(new Date((Long) item.get(field.getName()))));
                }
            }
            ((Result) result).setResult(item);
        }
    }

    public boolean isPrimitiveOrWrapper(Class<?> clazz) {
        return clazz.isPrimitive() || isWrapperType(clazz);
    }

    public boolean isWrapperType(Class<?> clazz) {
        return clazz == Boolean.class ||
                clazz == Character.class ||
                clazz == Byte.class ||
                clazz == Short.class ||
                clazz == Integer.class ||
                clazz == Long.class ||
                clazz == Float.class ||
                clazz == Double.class;
    }

}
