package org.design.core.tool.utils;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import org.springframework.lang.Nullable;
import org.springframework.util.StreamUtils;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/utils/IoUtil.class */
public class IoUtil extends StreamUtils {
    public static void closeQuietly(@Nullable Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }

    public static String toString(InputStream input) {
        return toString(input, Charsets.UTF_8);
    }

    public static String toString(@Nullable InputStream input, Charset charset) {
        try {
            try {
                String copyToString = copyToString(input, charset);
                closeQuietly(input);
                return copyToString;
            } catch (IOException e) {
                throw Exceptions.unchecked(e);
            }
        } catch (Throwable th) {
            closeQuietly(input);
            throw th;
        }
    }

    public static byte[] toByteArray(@Nullable InputStream input) {
        try {
            try {
                byte[] copyToByteArray = copyToByteArray(input);
                closeQuietly(input);
                return copyToByteArray;
            } catch (IOException e) {
                throw Exceptions.unchecked(e);
            }
        } catch (Throwable th) {
            closeQuietly(input);
            throw th;
        }
    }

    public static void write(@Nullable String data, OutputStream output, Charset encoding) throws IOException {
        if (data != null) {
            output.write(data.getBytes(encoding));
        }
    }
}
