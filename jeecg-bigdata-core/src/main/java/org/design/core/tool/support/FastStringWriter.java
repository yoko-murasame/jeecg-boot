package org.design.core.tool.support;

import java.io.Writer;
import org.design.core.tool.utils.StringPool;
import org.springframework.lang.Nullable;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/support/FastStringWriter.class */
public class FastStringWriter extends Writer {
    private StringBuilder builder;

    public FastStringWriter() {
        this.builder = new StringBuilder(64);
    }

    public FastStringWriter(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Negative builderfer size");
        }
        this.builder = new StringBuilder(capacity);
    }

    public FastStringWriter(@Nullable StringBuilder builder) {
        this.builder = builder != null ? builder : new StringBuilder(64);
    }

    public StringBuilder getBuilder() {
        return this.builder;
    }

    @Override // java.io.Writer
    public void write(int c) {
        this.builder.append((char) c);
    }

    @Override // java.io.Writer
    public void write(char[] cbuilder, int off, int len) {
        if (off < 0 || off > cbuilder.length || len < 0 || off + len > cbuilder.length || off + len < 0) {
            throw new IndexOutOfBoundsException();
        } else if (len != 0) {
            this.builder.append(cbuilder, off, len);
        }
    }

    @Override // java.io.Writer
    public void write(String str) {
        this.builder.append(str);
    }

    @Override // java.io.Writer
    public void write(String str, int off, int len) {
        this.builder.append(str.substring(off, off + len));
    }

    @Override // java.io.Writer, java.lang.Appendable
    public FastStringWriter append(CharSequence csq) {
        if (csq == null) {
            write(StringPool.NULL);
        } else {
            write(csq.toString());
        }
        return this;
    }

    @Override // java.io.Writer, java.lang.Appendable
    public FastStringWriter append(CharSequence csq, int start, int end) {
        write((csq == null ? StringPool.NULL : csq).subSequence(start, end).toString());
        return this;
    }

    @Override // java.io.Writer, java.lang.Appendable
    public FastStringWriter append(char c) {
        write(c);
        return this;
    }

    @Override // java.lang.Object
    public String toString() {
        return this.builder.toString();
    }

    @Override // java.io.Writer, java.io.Flushable
    public void flush() {
    }

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.builder.setLength(0);
        this.builder.trimToSize();
    }
}
