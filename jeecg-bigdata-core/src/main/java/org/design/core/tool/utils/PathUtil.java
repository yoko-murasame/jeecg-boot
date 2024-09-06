package org.design.core.tool.utils;

import java.io.File;
import java.net.URL;
import org.springframework.lang.Nullable;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/utils/PathUtil.class */
public class PathUtil {
    public static final String FILE_PROTOCOL = "file";
    public static final String JAR_PROTOCOL = "jar";
    public static final String ZIP_PROTOCOL = "zip";
    public static final String FILE_PROTOCOL_PREFIX = "file:";
    public static final String JAR_FILE_SEPARATOR = "!/";

    @Nullable
    public static String getJarPath() {
        try {
            return toFilePath(PathUtil.class.getResource(StringPool.SLASH).toURI().toURL());
        } catch (Exception e) {
            return new File(PathUtil.class.getResource(StringPool.EMPTY).getPath()).getParentFile().getParentFile().getAbsolutePath();
        }
    }

    @Nullable
    public static String toFilePath(@Nullable URL url) {
        if (url == null) {
            return null;
        }
        String protocol = url.getProtocol();
        String file = UrlUtil.decodeURL(url.getPath(), Charsets.UTF_8);
        if (FILE_PROTOCOL.equals(protocol)) {
            return new File(file).getParentFile().getParentFile().getAbsolutePath();
        }
        if (!JAR_PROTOCOL.equals(protocol) && !ZIP_PROTOCOL.equals(protocol)) {
            return file;
        }
        int ipos = file.indexOf(JAR_FILE_SEPARATOR);
        if (ipos > 0) {
            file = file.substring(0, ipos);
        }
        if (file.startsWith(FILE_PROTOCOL_PREFIX)) {
            file = file.substring(FILE_PROTOCOL_PREFIX.length());
        }
        return new File(file).getParentFile().getAbsolutePath();
    }
}
