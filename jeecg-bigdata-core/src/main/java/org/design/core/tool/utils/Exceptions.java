package org.design.core.tool.utils;

import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;
import org.design.core.tool.support.FastStringWriter;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/utils/Exceptions.class */
public class Exceptions {
    public static RuntimeException unchecked(Throwable e) {
        if ((e instanceof IllegalAccessException) || (e instanceof IllegalArgumentException) || (e instanceof NoSuchMethodException)) {
            return new IllegalArgumentException(e);
        }
        if (e instanceof InvocationTargetException) {
            return new RuntimeException(((InvocationTargetException) e).getTargetException());
        }
        if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        }
        return new RuntimeException(e);
    }

    public static Throwable unwrap(Throwable wrapped) {
        Throwable unwrapped = wrapped;
        while (true) {
            if (unwrapped instanceof InvocationTargetException) {
                unwrapped = ((InvocationTargetException) unwrapped).getTargetException();
            } else if (!(unwrapped instanceof UndeclaredThrowableException)) {
                return unwrapped;
            } else {
                unwrapped = ((UndeclaredThrowableException) unwrapped).getUndeclaredThrowable();
            }
        }
    }

    public static String getStackTraceAsString(Throwable ex) {
        FastStringWriter stringWriter = new FastStringWriter();
        ex.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }
}
