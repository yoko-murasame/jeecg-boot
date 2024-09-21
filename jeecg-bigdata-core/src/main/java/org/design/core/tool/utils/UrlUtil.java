package org.design.core.tool.utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import org.springframework.web.util.UriUtils;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/utils/UrlUtil.class */
public class UrlUtil extends UriUtils {
    public static String encodeURL(String source, Charset charset) {
        return encode(source, charset.name());
    }

    public static String decodeURL(String source, Charset charset) {
        return decode(source, charset.name());
    }

    public static String getPath(String uriStr) {
        try {
            return new URI(uriStr).getPath();
        } catch (URISyntaxException var3) {
            throw new RuntimeException(var3);
        }
    }
}
