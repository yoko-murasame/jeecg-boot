package org.design.core.tool.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.PackageVersion;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.design.core.tool.utils.DateTimeUtil;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/jackson/BaseJavaTimeModule.class */
public class BaseJavaTimeModule extends SimpleModule {
    public BaseJavaTimeModule() {
        super(PackageVersion.VERSION);
        addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeUtil.DATETIME_FORMAT));
        addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeUtil.DATE_FORMAT));
        addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeUtil.TIME_FORMAT));
        addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeUtil.DATETIME_FORMAT));
        addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeUtil.DATE_FORMAT));
        addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeUtil.TIME_FORMAT));
    }
}
