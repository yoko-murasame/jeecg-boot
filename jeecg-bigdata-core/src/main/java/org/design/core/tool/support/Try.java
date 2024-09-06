package org.design.core.tool.support;

import java.util.Objects;
import java.util.function.Function;
import org.design.core.tool.utils.Exceptions;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/support/Try.class */
public class Try {

    @FunctionalInterface
    /* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/support/Try$UncheckedFunction.class */
    public interface UncheckedFunction<T, R> {
        R apply(T t) throws Exception;
    }

    public static <T, R> Function<T, R> of(UncheckedFunction<T, R> mapper) {
        Objects.requireNonNull(mapper);
        return t -> {
            try {
                return mapper.apply(t);
            } catch (Exception e) {
                throw Exceptions.unchecked(e);
            }
        };
    }
}
