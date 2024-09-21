package org.design.core.tool.utils;

import java.io.Serializable;
import java.util.function.Supplier;
import org.springframework.lang.Nullable;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/utils/Lazy.class */
public class Lazy<T> implements Supplier<T>, Serializable {
    @Nullable
    private volatile transient Supplier<? extends T> supplier;
    @Nullable
    private T value;

    public static <T> Lazy<T> of(Supplier<T> supplier) {
        return new Lazy<>(supplier);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r4v0, resolved type: java.util.function.Supplier<T> */
    /* JADX WARN: Multi-variable type inference failed */
    private Lazy(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    @Override // java.util.function.Supplier
    @Nullable
    public T get() {
        return this.supplier == null ? this.value : computeValue();
    }

    @Nullable
    private synchronized T computeValue() {
        Supplier<? extends T> s = this.supplier;
        if (s != null) {
            this.value = (T) s.get();
            this.supplier = null;
        }
        return this.value;
    }
}
