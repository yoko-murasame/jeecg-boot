package org.design.core.tool.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.design.core.tool.utils.StringPool;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/jackson/BaseBeanSerializerModifier.class */
public class BaseBeanSerializerModifier extends BeanSerializerModifier {

    /* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/jackson/BaseBeanSerializerModifier$NullJsonSerializers.class */
    public interface NullJsonSerializers {
        public static final JsonSerializer<Object> STRING_JSON_SERIALIZER = new JsonSerializer<Object>() { // from class: org.design.core.tool.jackson.BaseBeanSerializerModifier.NullJsonSerializers.1
            public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeString(StringPool.EMPTY);
            }
        };
        public static final JsonSerializer<Object> NUMBER_JSON_SERIALIZER = new JsonSerializer<Object>() { // from class: org.design.core.tool.jackson.BaseBeanSerializerModifier.NullJsonSerializers.2
            public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeNumber(-1);
            }
        };
        public static final JsonSerializer<Object> BOOLEAN_JSON_SERIALIZER = new JsonSerializer<Object>() { // from class: org.design.core.tool.jackson.BaseBeanSerializerModifier.NullJsonSerializers.3
            public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeObject(Boolean.FALSE);
            }
        };
        public static final JsonSerializer<Object> ARRAY_JSON_SERIALIZER = new JsonSerializer<Object>() { // from class: org.design.core.tool.jackson.BaseBeanSerializerModifier.NullJsonSerializers.4
            public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeStartArray();
                gen.writeEndArray();
            }
        };
        public static final JsonSerializer<Object> OBJECT_JSON_SERIALIZER = new JsonSerializer<Object>() { // from class: org.design.core.tool.jackson.BaseBeanSerializerModifier.NullJsonSerializers.5
            public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeStartObject();
                gen.writeEndObject();
            }
        };
    }

    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
        beanProperties.forEach(writer -> {
            if (!writer.hasNullSerializer()) {
                JavaType type = writer.getType();
                Class<?> clazz = type.getRawClass();
                if (type.isTypeOrSubTypeOf(Number.class)) {
                    writer.assignNullSerializer(NullJsonSerializers.NUMBER_JSON_SERIALIZER);
                } else if (type.isTypeOrSubTypeOf(Boolean.class)) {
                    writer.assignNullSerializer(NullJsonSerializers.BOOLEAN_JSON_SERIALIZER);
                } else if (type.isTypeOrSubTypeOf(Character.class)) {
                    writer.assignNullSerializer(NullJsonSerializers.STRING_JSON_SERIALIZER);
                } else if (type.isTypeOrSubTypeOf(String.class)) {
                    writer.assignNullSerializer(NullJsonSerializers.STRING_JSON_SERIALIZER);
                } else if (type.isArrayType() || clazz.isArray() || type.isTypeOrSubTypeOf(Collection.class)) {
                    writer.assignNullSerializer(NullJsonSerializers.ARRAY_JSON_SERIALIZER);
                } else if (type.isTypeOrSubTypeOf(OffsetDateTime.class)) {
                    writer.assignNullSerializer(NullJsonSerializers.STRING_JSON_SERIALIZER);
                } else if (type.isTypeOrSubTypeOf(Date.class) || type.isTypeOrSubTypeOf(TemporalAccessor.class)) {
                    writer.assignNullSerializer(NullJsonSerializers.STRING_JSON_SERIALIZER);
                } else {
                    writer.assignNullSerializer(NullJsonSerializers.OBJECT_JSON_SERIALIZER);
                }
            }
        });
        return changeProperties(config, beanDesc, beanProperties);
    }
}
