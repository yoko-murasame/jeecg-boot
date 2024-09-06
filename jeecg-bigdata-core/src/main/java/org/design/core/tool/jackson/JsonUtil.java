package org.design.core.tool.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import org.design.core.tool.utils.DateUtil;
import org.design.core.tool.utils.Exceptions;
import org.design.core.tool.utils.StringPool;
import org.design.core.tool.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/jackson/JsonUtil.class */
public class JsonUtil {
    private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);

    public static <T> String toJson(T value) {
        try {
            return getInstance().writeValueAsString(value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public static byte[] toJsonAsBytes(Object object) {
        try {
            return getInstance().writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            throw Exceptions.unchecked(e);
        }
    }

    public static <T> T parse(String content, Class<T> valueType) {
        try {
            return (T) getInstance().readValue(content, valueType);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public static <T> T parse(String content, TypeReference<T> typeReference) {
        try {
            return (T) getInstance().readValue(content, typeReference);
        } catch (IOException e) {
            throw Exceptions.unchecked(e);
        }
    }

    public static <T> T parse(byte[] bytes, Class<T> valueType) {
        try {
            return (T) getInstance().readValue(bytes, valueType);
        } catch (IOException e) {
            throw Exceptions.unchecked(e);
        }
    }

    public static <T> T parse(byte[] bytes, TypeReference<T> typeReference) {
        try {
            return (T) getInstance().readValue(bytes, typeReference);
        } catch (IOException e) {
            throw Exceptions.unchecked(e);
        }
    }

    public static <T> T parse(InputStream in, Class<T> valueType) {
        try {
            return (T) getInstance().readValue(in, valueType);
        } catch (IOException e) {
            throw Exceptions.unchecked(e);
        }
    }

    public static <T> T parse(InputStream in, TypeReference<T> typeReference) {
        try {
            return (T) getInstance().readValue(in, typeReference);
        } catch (IOException e) {
            throw Exceptions.unchecked(e);
        }
    }

    public static <T> List<T> parseArray(String content, Class<T> valueTypeRef) {
        try {
            if (!StringUtil.startsWithIgnoreCase(content, StringPool.LEFT_SQ_BRACKET)) {
                content = StringPool.LEFT_SQ_BRACKET + content + StringPool.RIGHT_SQ_BRACKET;
            }
            List<Map<String, Object>> list = (List) getInstance().readValue(content, new TypeReference<List<Map<String, Object>>>() { // from class: org.design.core.tool.jackson.JsonUtil.1
            });
            ArrayList arrayList = new ArrayList();
            for (Map<String, Object> map : list) {
                arrayList.add(toPojo(map, valueTypeRef));
            }
            return arrayList;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public static Map<String, Object> toMap(String content) {
        try {
            return (Map) getInstance().readValue(content, Map.class);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public static <T> Map<String, T> toMap(String content, Class<T> valueTypeRef) {
        try {
            Map<String, Map<String, Object>> map = (Map) getInstance().readValue(content, new TypeReference<Map<String, Map<String, Object>>>() { // from class: org.design.core.tool.jackson.JsonUtil.2
            });
            HashMap hashMap = new HashMap(16);
            for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
                hashMap.put(entry.getKey(), toPojo(entry.getValue(), valueTypeRef));
            }
            return hashMap;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public static <T> T toPojo(Map fromValue, Class<T> toValueType) {
        return (T) getInstance().convertValue(fromValue, toValueType);
    }

    public static JsonNode readTree(String jsonString) {
        try {
            return getInstance().readTree(jsonString);
        } catch (IOException e) {
            throw Exceptions.unchecked(e);
        }
    }

    public static JsonNode readTree(InputStream in) {
        try {
            return getInstance().readTree(in);
        } catch (IOException e) {
            throw Exceptions.unchecked(e);
        }
    }

    public static JsonNode readTree(byte[] content) {
        try {
            return getInstance().readTree(content);
        } catch (IOException e) {
            throw Exceptions.unchecked(e);
        }
    }

    public static JsonNode readTree(JsonParser jsonParser) {
        try {
            return getInstance().readTree(jsonParser);
        } catch (IOException e) {
            throw Exceptions.unchecked(e);
        }
    }

    public static ObjectMapper getInstance() {
        return JacksonHolder.INSTANCE;
    }

    /* access modifiers changed from: private */
    /* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/jackson/JsonUtil$JacksonHolder.class */
    public static class JacksonHolder {
        private static ObjectMapper INSTANCE = new JacksonObjectMapper();

        private JacksonHolder() {
        }
    }

    /* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/jackson/JsonUtil$JacksonObjectMapper.class */
    public static class JacksonObjectMapper extends ObjectMapper {
        private static final long serialVersionUID = 4288193147502386170L;
        private static final Locale CHINA = Locale.CHINA;

        public JacksonObjectMapper() {
            setLocale(CHINA);
            configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
            setDateFormat(new SimpleDateFormat(DateUtil.PATTERN_DATETIME, Locale.CHINA));
            configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
            configure(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER.mappedFeature(), true);
            findAndRegisterModules();
            configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
            getDeserializationConfig().withoutFeatures(new DeserializationFeature[]{DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES});
            registerModule(new BaseJavaTimeModule());
            findAndRegisterModules();
        }

        public ObjectMapper copy() {
            return copy();
        }
    }
}
