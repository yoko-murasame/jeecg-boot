package org.design.core.tool.api;

import java.io.Serializable;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/api/IResultCode.class */
public interface IResultCode extends Serializable {
    String getMessage();

    int getCode();
}
