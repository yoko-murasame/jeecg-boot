package org.design.core.tool.support;

import org.design.core.tool.utils.Func;
import org.design.core.tool.utils.StringPool;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/support/StrFormatter.class */
public class StrFormatter {
    public static String format(String strPattern, Object... argArray) {
        int i;
        int i2;
        if (Func.isBlank(strPattern) || Func.isEmpty(argArray)) {
            return strPattern;
        }
        int strPatternLength = strPattern.length();
        StringBuilder sbuf = new StringBuilder(strPatternLength + 50);
        int handledPosition = 0;
        int argIndex = 0;
        while (argIndex < argArray.length) {
            int delimIndex = strPattern.indexOf(StringPool.EMPTY_JSON, handledPosition);
            if (delimIndex != -1) {
                if (delimIndex <= 0 || strPattern.charAt(delimIndex - 1) != '\\') {
                    sbuf.append((CharSequence) strPattern, handledPosition, delimIndex);
                    sbuf.append(Func.toStr(argArray[argIndex]));
                    i2 = delimIndex;
                    i = 2;
                } else if (delimIndex <= 1 || strPattern.charAt(delimIndex - 2) != '\\') {
                    argIndex--;
                    sbuf.append((CharSequence) strPattern, handledPosition, delimIndex - 1);
                    sbuf.append(StringPool.LEFT_BRACE);
                    i2 = delimIndex;
                    i = 1;
                } else {
                    sbuf.append((CharSequence) strPattern, handledPosition, delimIndex - 1);
                    sbuf.append(Func.toStr(argArray[argIndex]));
                    i2 = delimIndex;
                    i = 2;
                }
                handledPosition = i2 + i;
                argIndex++;
            } else if (handledPosition == 0) {
                return strPattern;
            } else {
                sbuf.append((CharSequence) strPattern, handledPosition, strPatternLength);
                return sbuf.toString();
            }
        }
        sbuf.append((CharSequence) strPattern, handledPosition, strPattern.length());
        return sbuf.toString();
    }
}
