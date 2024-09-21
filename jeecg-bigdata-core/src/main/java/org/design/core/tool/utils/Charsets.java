package org.design.core.tool.utils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/utils/Charsets.class */
public class Charsets {
    public static final Charset ISO_8859_1 = StandardCharsets.ISO_8859_1;
    public static final String ISO_8859_1_NAME = ISO_8859_1.name();
    public static final Charset GBK = Charset.forName(StringPool.GBK);
    public static final String GBK_NAME = GBK.name();
    public static final Charset UTF_8 = StandardCharsets.UTF_8;
    public static final String UTF_8_NAME = UTF_8.name();

    public static Charset charset(String charsetName) throws UnsupportedCharsetException {
        return StringUtil.isBlank(charsetName) ? Charset.defaultCharset() : Charset.forName(charsetName);
    }
}
