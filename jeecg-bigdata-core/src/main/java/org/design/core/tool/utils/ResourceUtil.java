package org.design.core.tool.utils;

import java.io.IOException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/utils/ResourceUtil.class */
public class ResourceUtil extends ResourceUtils {
    public static final String HTTP_REGEX = "^https?:.+$";
    public static final String FTP_URL_PREFIX = "ftp:";

    public static Resource getResource(String resourceLocation) throws IOException {
        Assert.notNull(resourceLocation, "Resource location must not be null");
        if (resourceLocation.startsWith("classpath:")) {
            return new ClassPathResource(resourceLocation);
        }
        if (resourceLocation.startsWith(FTP_URL_PREFIX)) {
            return new UrlResource(resourceLocation);
        }
        if (resourceLocation.matches(HTTP_REGEX)) {
            return new UrlResource(resourceLocation);
        }
        if (resourceLocation.startsWith("classpath*:")) {
            return SpringUtil.getContext().getResource(resourceLocation);
        }
        return new FileSystemResource(resourceLocation);
    }
}
