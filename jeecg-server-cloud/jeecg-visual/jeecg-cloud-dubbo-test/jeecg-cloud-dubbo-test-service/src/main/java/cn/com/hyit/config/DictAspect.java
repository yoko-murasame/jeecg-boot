package cn.com.hyit.config;

import cn.com.hyit.config.anno.Dict;
import cn.com.hyit.config.constant.CommonConstant;
import cn.com.hyit.config.util.oConvertUtils;
import cn.com.hyit.config.vo.DictModel;
import cn.com.hyit.core.base.bean.RpcResult;
import cn.com.hyit.core.web.response.WebResponse;
import cn.com.hyit.service.system.ISysDictService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 字典aop类
 * @author YOKO
 */
@Aspect
@Component
@Slf4j
public class DictAspect {

    @Autowired
    private ISysDictService sysDictService;
    @Autowired
    public RedisTemplate redisTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 定义切点Pointcut
     */
    @Pointcut("execution(public * cn.com.hyit.rest..*.*Rest.*(..))")
    public void restService() {
    }

    @Pointcut("execution(public * cn.com.hyit.provider..*.*RpcService.*(..))")
    public void rpcService() {
    }

    @Pointcut("@annotation(cn.com.hyit.config.anno.AutoDict)")
    public void annoService() {
    }


    @Around("restService() || rpcService() || annoService()")
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

    /**
     * 本方法针对返回对象为 WebResponse 的 IPage | List 进行动态字典注入
     * 字典注入实现 通过对实体类添加注解@dict 来标识需要的字典内容
     * 示例为SysUser   字段为sex 添加了注解@Dict(dicCode = "sex")
     * 例输入当前返回值的就会多出一个sex_dictText字段
     * {
     * sex:1,
     * sex_dictText:"男"
     * }
     *
     */
    private Object parseDictText(Object result) {

        List<JSONObject> items = new ArrayList<>();
        // step.1 筛选出加了 Dict 注解的字段列表
        List<Field> dictFieldList = new ArrayList<>();
        // 字典数据列表， key = 字典code，value=数据列表
        Map<String, List<String>> dataListMap = new HashMap<>(5);

        // rest
        if (result instanceof WebResponse) {
            if (((WebResponse<?>) result).getResult() instanceof IPage) {
                // 取出结果集
                List<?> records = ((IPage<?>) ((WebResponse<?>) result).getResult()).getRecords();
                Boolean hasDict = checkHasDict(records);
                if (!hasDict) {
                    return result;
                }
                // 翻译字典
                handleDictTranslate(records, dictFieldList, dataListMap, items);
                ((IPage) ((WebResponse<?>) result).getResult()).setRecords(items);
            }else if (((WebResponse<?>) result).getResult() instanceof List) {
                // 取出结果集
                List<?> records = (List<?>) ((WebResponse<?>) result).getResult();
                Boolean hasDict = checkHasDict(records);
                if (!hasDict) {
                    return result;
                }
                // 翻译字典
                handleDictTranslate(records, dictFieldList, dataListMap, items);
                ((WebResponse) result).setResult(items);
            } else {
                // 排除字符串和基础类型
                Object record = ((WebResponse<?>) result).getResult();
                if (null == record || record instanceof String || this.isPrimitiveOrWrapper(record.getClass())) {
                    return result;
                }
                // 翻译对象
                handleDictTranslate(Collections.singletonList(record), dictFieldList, dataListMap, items);
                ((WebResponse) result).setResult(items.get(0));
            }
        }
        // rpc
        if (result instanceof RpcResult) {
            if (((RpcResult<?>) result).getData() instanceof IPage) {
                // 取出结果集
                List<?> records = ((IPage<?>) ((RpcResult<?>) result).getData()).getRecords();
                Boolean hasDict = checkHasDict(records);
                if (!hasDict) {
                    return result;
                }
                // 翻译字典
                handleDictTranslate(records, dictFieldList, dataListMap, items);
                ((IPage) ((RpcResult<?>) result).getData()).setRecords(items);
            } else if (((RpcResult<?>) result).getData() instanceof List) {
                // 取出结果集
                List<?> records = (List<?>) ((RpcResult<?>) result).getData();
                Boolean hasDict = checkHasDict(records);
                if (!hasDict) {
                    return result;
                }
                // 翻译字典
                handleDictTranslate(records, dictFieldList, dataListMap, items);
                ((RpcResult) result).setData(items);
            } else {
                // 排除字符串和基础类型
                Object record = ((RpcResult<?>) result).getData();
                if (null == record || record instanceof String || this.isPrimitiveOrWrapper(record.getClass())) {
                    return result;
                }
                // 翻译对象
                handleDictTranslate(Collections.singletonList(record), dictFieldList, dataListMap, items);
                ((RpcResult) result).setData(items.get(0));
            }
        }
        return result;
    }

    private void handleDictTranslate(List<?> records, List<Field> dictFieldList, Map<String, List<String>> dataListMap, List<JSONObject> items) {
        log.debug(" __ 进入字典翻译切面 DictAspect —— ");
        for (Object record : records) {
            String json = "{}";
            try {
                json = objectMapper.writeValueAsString(record);
            } catch (JsonProcessingException e) {
                log.error("json解析失败" + e.getMessage(), e);
            }
            JSONObject item = JSONObject.parseObject(json, Feature.OrderedField);

            // 遍历所有字段，把字典Code取出来，放到 map 里
            for (Field field : oConvertUtils.getAllFields(record)) {
                String value = item.getString(field.getName());
                if (oConvertUtils.isEmpty(value)) {
                    continue;
                }
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

                    // TODO 测试输出，待删
                    log.debug(" ---- dictCode: " + fieldDictCode);
                    log.debug(" ---- value: " + value);
                    log.debug(" ----- text: " + textValue);
                    log.debug(" ---- dictModels: " + JSON.toJSONString(dictModels));

                    record.put(field.getName() + CommonConstant.DICT_TEXT_SUFFIX, textValue);
                }
            }
        }
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
     */
    private Map<String, List<DictModel>> translateAllDict(Map<String, List<String>> dataListMap) {
        // 翻译后的字典文本，key=dictCode
        Map<String, List<DictModel>> translText = new HashMap<>(5);
        // 需要翻译的数据（有些可以从redis缓存中获取，就不走数据库查询）
        List<String> needTranslData = new ArrayList<>();
        // step.1 先通过redis中获取缓存字典数据
        for (String dictCode : dataListMap.keySet()) {
            List<String> dataList = dataListMap.get(dictCode);
            if (dataList.size() == 0) {
                continue;
            }
            // 表字典需要翻译的数据
            List<String> needTranslDataTable = new ArrayList<>();
            for (String s : dataList) {
                String data = s.trim();
                if (data.length() == 0) {
                    continue; // 跳过循环
                }
                if (dictCode.contains(",")) {
                    String keyString = String.format("sys:cache:dictTable::SimpleKey [%s,%s]", dictCode, data);
                    if (redisTemplate.hasKey(keyString)) {
                        try {
                            String text = oConvertUtils.getString(redisTemplate.opsForValue().get(keyString));
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
                    String keyString = String.format("sys:cache:dict::%s:%s", dictCode, data);
                    if (redisTemplate.hasKey(keyString)) {
                        try {
                            String text = oConvertUtils.getString(redisTemplate.opsForValue().get(keyString));
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
            if (needTranslDataTable.size() > 0) {
                String[] arr = dictCode.split(",");
                String table = arr[0], text = arr[1], code = arr[2];
                String values = String.join(",", needTranslDataTable);
                log.debug("translateDictFromTableByKeys.dictCode:" + dictCode);
                log.debug("translateDictFromTableByKeys.values:" + values);
                List<DictModel> texts = sysDictService.translateDictFromTableByKeys(table, text, code, values);
                log.debug("translateDictFromTableByKeys.result:" + texts);
                List<DictModel> list = translText.computeIfAbsent(dictCode, k -> new ArrayList<>());
                list.addAll(texts);

                // 做 redis 缓存
                for (DictModel dict : texts) {
                    String redisKey = String.format("sys:cache:dictTable::SimpleKey [%s,%s]", dictCode, dict.getValue());
                    try {
                        // 保留5分钟
                        redisTemplate.opsForValue().set(redisKey, dict.getText(), 300, TimeUnit.SECONDS);
                    } catch (Exception e) {
                        log.warn(e.getMessage(), e);
                    }
                }
            }
        }

        // step.3 调用数据库进行翻译普通字典
        if (needTranslData.size() > 0) {
            List<String> dictCodeList = Arrays.asList(dataListMap.keySet().toArray(new String[]{}));
            // 将不包含逗号的字典code筛选出来，因为带逗号的是表字典，而不是普通的数据字典
            List<String> filterDictCodes = dictCodeList.stream().filter(key -> !key.contains(",")).collect(Collectors.toList());
            String dictCodes = String.join(",", filterDictCodes);
            String values = String.join(",", needTranslData);
            log.debug("translateManyDict.dictCodes:" + dictCodes);
            log.debug("translateManyDict.values:" + values);
            Map<String, List<DictModel>> manyDict = sysDictService.translateManyDict(dictCodes, values);
            log.debug("translateManyDict.result:" + manyDict);
            for (String dictCode : manyDict.keySet()) {
                List<DictModel> list = translText.computeIfAbsent(dictCode, k -> new ArrayList<>());
                List<DictModel> newList = manyDict.get(dictCode);
                list.addAll(newList);

                // 做 redis 缓存
                for (DictModel dict : newList) {
                    String redisKey = String.format("sys:cache:dict::%s:%s", dictCode, dict.getValue());
                    try {
                        redisTemplate.opsForValue().set(redisKey, dict.getText());
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
     */
    private String translDictText(List<DictModel> dictModels, String values) {
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
     * 检测返回结果集中是否包含Dict注解
     */
    private Boolean checkHasDict(List<?> records) {
        if (oConvertUtils.isNotEmpty(records) && records.size() > 0) {
            for (Field field : oConvertUtils.getAllFields(records.get(0))) {
                if (oConvertUtils.isNotEmpty(field.getAnnotation(Dict.class))) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isPrimitiveOrWrapper(Class<?> clazz) {
        return clazz.isPrimitive() || isWrapperType(clazz);
    }

    private boolean isWrapperType(Class<?> clazz) {
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
