package org.design.core.tool.convert;

import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.lang.Nullable;
import org.springframework.util.StringValueResolver;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/convert/BaseConversionService.class */
public class BaseConversionService extends ApplicationConversionService {
    @Nullable
    private static volatile BaseConversionService SHARED_INSTANCE;

    public BaseConversionService() {
        this(null);
    }

    public BaseConversionService(@Nullable StringValueResolver embeddedValueResolver) {
        super(embeddedValueResolver);
        addConverter(new EnumToStringConverter());
        addConverter(new StringToEnumConverter());
    }

    public static GenericConversionService getInstance() {
        BaseConversionService sharedInstance = SHARED_INSTANCE;
        if (sharedInstance == null) {
            synchronized (BaseConversionService.class) {
                sharedInstance = SHARED_INSTANCE;
                if (sharedInstance == null) {
                    sharedInstance = new BaseConversionService();
                    SHARED_INSTANCE = sharedInstance;
                }
            }
        }
        return sharedInstance;
    }
}
