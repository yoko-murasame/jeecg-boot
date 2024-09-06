package org.design.core.tool.support;

import java.io.OutputStream;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/support/IMultiOutputStream.class */
public interface IMultiOutputStream {
    OutputStream buildOutputStream(Integer... numArr);
}
