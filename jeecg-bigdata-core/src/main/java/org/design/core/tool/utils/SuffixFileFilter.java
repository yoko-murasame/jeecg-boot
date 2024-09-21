package org.design.core.tool.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;
import org.springframework.util.Assert;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/utils/SuffixFileFilter.class */
public class SuffixFileFilter implements FileFilter, Serializable {
    private static final long serialVersionUID = -3389157631240246157L;
    private final String[] suffixes;

    public SuffixFileFilter(String suffix) {
        Assert.notNull(suffix, "The suffix must not be null");
        this.suffixes = new String[]{suffix};
    }

    public SuffixFileFilter(String[] suffixes) {
        Assert.notNull(suffixes, "The suffix must not be null");
        this.suffixes = new String[suffixes.length];
        System.arraycopy(suffixes, 0, this.suffixes, 0, suffixes.length);
    }

    @Override // java.io.FileFilter
    public boolean accept(File pathname) {
        String name = pathname.getName();
        for (String suffix : this.suffixes) {
            if (checkEndsWith(name, suffix)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkEndsWith(String str, String end) {
        int endLen = end.length();
        return str.regionMatches(true, str.length() - endLen, end, 0, endLen);
    }
}
