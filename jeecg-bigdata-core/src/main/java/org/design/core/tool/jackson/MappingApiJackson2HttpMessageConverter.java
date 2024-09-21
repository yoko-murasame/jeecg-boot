package org.design.core.tool.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.design.core.tool.jackson.BaseBeanSerializerModifier;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.lang.Nullable;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/jackson/MappingApiJackson2HttpMessageConverter.class */
public class MappingApiJackson2HttpMessageConverter extends AbstractReadWriteJackson2HttpMessageConverter {
    @Nullable
    private String jsonPrefix;

    public MappingApiJackson2HttpMessageConverter() {
        this(Jackson2ObjectMapperBuilder.json().build());
    }

    public MappingApiJackson2HttpMessageConverter(ObjectMapper objectMapper) {
        super(objectMapper, initWriteObjectMapper(objectMapper), MediaType.APPLICATION_JSON, new MediaType("application", "*+json"));
    }

    private static ObjectMapper initWriteObjectMapper(ObjectMapper readObjectMapper) {
        ObjectMapper writeObjectMapper = readObjectMapper.copy();
        writeObjectMapper.setSerializerFactory(writeObjectMapper.getSerializerFactory().withSerializerModifier(new BaseBeanSerializerModifier()));
        writeObjectMapper.getSerializerProvider().setNullValueSerializer(BaseBeanSerializerModifier.NullJsonSerializers.STRING_JSON_SERIALIZER);
        return writeObjectMapper;
    }

    public void setJsonPrefix(String jsonPrefix) {
        this.jsonPrefix = jsonPrefix;
    }

    public void setPrefixJson(boolean prefixJson) {
        this.jsonPrefix = prefixJson ? ")]}', " : null;
    }

    protected void writePrefix(JsonGenerator generator, Object object) throws IOException {
        if (this.jsonPrefix != null) {
            generator.writeRaw(this.jsonPrefix);
        }
    }
}
