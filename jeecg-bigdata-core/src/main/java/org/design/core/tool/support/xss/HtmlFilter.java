package org.design.core.tool.support.xss;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.design.core.tool.utils.StringPool;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/support/xss/HtmlFilter.class */
public final class HtmlFilter {
    private static final int REGEX_FLAGS_SI = 34;
    private static final Pattern P_COMMENTS;
    private static final Pattern P_COMMENT;
    private static final Pattern P_TAGS;
    private static final Pattern P_END_TAG;
    private static final Pattern P_START_TAG;
    private static final Pattern P_QUOTED_ATTRIBUTES;
    private static final Pattern P_UNQUOTED_ATTRIBUTES;
    private static final Pattern P_PROTOCOL;
    private static final Pattern P_ENTITY;
    private static final Pattern P_ENTITY_UNICODE;
    private static final Pattern P_ENCODE;
    private static final Pattern P_VALID_ENTITIES;
    private static final Pattern P_VALID_QUOTES;
    private static final Pattern P_END_ARROW;
    private static final Pattern P_BODY_TO_END;
    private static final Pattern P_XML_CONTENT;
    private static final Pattern P_STRAY_LEFT_ARROW;
    private static final Pattern P_STRAY_RIGHT_ARROW;
    private static final Pattern P_AMP;
    private static final Pattern P_QUOTE;
    private static final Pattern P_LEFT_ARROW;
    private static final Pattern P_RIGHT_ARROW;
    private static final Pattern P_BOTH_ARROWS;
    private static final ConcurrentMap<String, Pattern> P_REMOVE_PAIR_BLANKS;
    private static final ConcurrentMap<String, Pattern> P_REMOVE_SELF_BLANKS;
    private final Map<String, List<String>> vAllowed;
    private final Map<String, Integer> vTagCounts;
    private final String[] vSelfClosingTags;
    private final String[] vNeedClosingTags;
    private final String[] vDisallowed;
    private final String[] vProtocolAtts;
    private final String[] vAllowedProtocols;
    private final String[] vRemoveBlanks;
    private final String[] vAllowedEntities;
    private final boolean stripComment;
    private final boolean encodeQuotes;
    private boolean vDebug;
    private final boolean alwaysMakeTags;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !HtmlFilter.class.desiredAssertionStatus();
        P_COMMENTS = Pattern.compile("<!--(.*?)-->", 32);
        P_COMMENT = Pattern.compile("^!--(.*)--$", REGEX_FLAGS_SI);
        P_TAGS = Pattern.compile("<(.*?)>", 32);
        P_END_TAG = Pattern.compile("^/([a-z0-9]+)", REGEX_FLAGS_SI);
        P_START_TAG = Pattern.compile("^([a-z0-9]+)(.*?)(/?)$", REGEX_FLAGS_SI);
        P_QUOTED_ATTRIBUTES = Pattern.compile("([a-z0-9]+)=([\"'])(.*?)\\2", REGEX_FLAGS_SI);
        P_UNQUOTED_ATTRIBUTES = Pattern.compile("([a-z0-9]+)(=)([^\"\\s']+)", REGEX_FLAGS_SI);
        P_PROTOCOL = Pattern.compile("^([^:]+):", REGEX_FLAGS_SI);
        P_ENTITY = Pattern.compile("&#(\\d+);?");
        P_ENTITY_UNICODE = Pattern.compile("&#x([0-9a-f]+);?");
        P_ENCODE = Pattern.compile("%([0-9a-f]{2});?");
        P_VALID_ENTITIES = Pattern.compile("&([^&;]*)(?=(;|&|$))");
        P_VALID_QUOTES = Pattern.compile("(>|^)([^<]+?)(<|$)", 32);
        P_END_ARROW = Pattern.compile("^>");
        P_BODY_TO_END = Pattern.compile("<([^>]*?)(?=<|$)");
        P_XML_CONTENT = Pattern.compile("(^|>)([^<]*?)(?=>)");
        P_STRAY_LEFT_ARROW = Pattern.compile("<([^>]*?)(?=<|$)");
        P_STRAY_RIGHT_ARROW = Pattern.compile("(^|>)([^<]*?)(?=>)");
        P_AMP = Pattern.compile(StringPool.AMPERSAND);
        P_QUOTE = Pattern.compile(StringPool.LEFT_CHEV);
        P_LEFT_ARROW = Pattern.compile(StringPool.LEFT_CHEV);
        P_RIGHT_ARROW = Pattern.compile(StringPool.RIGHT_CHEV);
        P_BOTH_ARROWS = Pattern.compile("<>");
        P_REMOVE_PAIR_BLANKS = new ConcurrentHashMap();
        P_REMOVE_SELF_BLANKS = new ConcurrentHashMap();
    }

    public HtmlFilter() {
        this.vTagCounts = new HashMap();
        this.vDebug = false;
        this.vAllowed = new HashMap();
        ArrayList<String> aAtts = new ArrayList<>();
        aAtts.add("href");
        aAtts.add("target");
        this.vAllowed.put("a", aAtts);
        ArrayList<String> imgAtts = new ArrayList<>();
        imgAtts.add("src");
        imgAtts.add("width");
        imgAtts.add("height");
        imgAtts.add("alt");
        this.vAllowed.put("img", imgAtts);
        ArrayList<String> noAtts = new ArrayList<>();
        this.vAllowed.put("b", noAtts);
        this.vAllowed.put("strong", noAtts);
        this.vAllowed.put("i", noAtts);
        this.vAllowed.put("em", noAtts);
        this.vSelfClosingTags = new String[]{"img"};
        this.vNeedClosingTags = new String[]{"a", "b", "strong", "i", "em"};
        this.vDisallowed = new String[0];
        this.vAllowedProtocols = new String[]{"http", "mailto", "https"};
        this.vProtocolAtts = new String[]{"src", "href"};
        this.vRemoveBlanks = new String[]{"a", "b", "strong", "i", "em"};
        this.vAllowedEntities = new String[]{"amp", "gt", "lt", "quot"};
        this.stripComment = true;
        this.encodeQuotes = true;
        this.alwaysMakeTags = true;
    }

    public HtmlFilter(boolean debug) {
        this();
        this.vDebug = debug;
    }

    public HtmlFilter(Map<String, Object> conf) {
        this.vTagCounts = new HashMap();
        this.vDebug = false;
        if (!$assertionsDisabled && !conf.containsKey("vAllowed")) {
            throw new AssertionError("configuration requires vAllowed");
        } else if (!$assertionsDisabled && !conf.containsKey("vSelfClosingTags")) {
            throw new AssertionError("configuration requires vSelfClosingTags");
        } else if (!$assertionsDisabled && !conf.containsKey("vNeedClosingTags")) {
            throw new AssertionError("configuration requires vNeedClosingTags");
        } else if (!$assertionsDisabled && !conf.containsKey("vDisallowed")) {
            throw new AssertionError("configuration requires vDisallowed");
        } else if (!$assertionsDisabled && !conf.containsKey("vAllowedProtocols")) {
            throw new AssertionError("configuration requires vAllowedProtocols");
        } else if (!$assertionsDisabled && !conf.containsKey("vProtocolAtts")) {
            throw new AssertionError("configuration requires vProtocolAtts");
        } else if (!$assertionsDisabled && !conf.containsKey("vRemoveBlanks")) {
            throw new AssertionError("configuration requires vRemoveBlanks");
        } else if ($assertionsDisabled || conf.containsKey("vAllowedEntities")) {
            this.vAllowed = Collections.unmodifiableMap((HashMap) conf.get("vAllowed"));
            this.vSelfClosingTags = (String[]) conf.get("vSelfClosingTags");
            this.vNeedClosingTags = (String[]) conf.get("vNeedClosingTags");
            this.vDisallowed = (String[]) conf.get("vDisallowed");
            this.vAllowedProtocols = (String[]) conf.get("vAllowedProtocols");
            this.vProtocolAtts = (String[]) conf.get("vProtocolAtts");
            this.vRemoveBlanks = (String[]) conf.get("vRemoveBlanks");
            this.vAllowedEntities = (String[]) conf.get("vAllowedEntities");
            this.stripComment = conf.containsKey("stripComment") ? ((Boolean) conf.get("stripComment")).booleanValue() : true;
            this.encodeQuotes = conf.containsKey("encodeQuotes") ? ((Boolean) conf.get("encodeQuotes")).booleanValue() : true;
            this.alwaysMakeTags = conf.containsKey("alwaysMakeTags") ? ((Boolean) conf.get("alwaysMakeTags")).booleanValue() : true;
        } else {
            throw new AssertionError("configuration requires vAllowedEntities");
        }
    }

    private void reset() {
        this.vTagCounts.clear();
    }

    private void debug(String msg) {
        if (this.vDebug) {
            Logger.getAnonymousLogger().info(msg);
        }
    }

    public static String chr(int decimal) {
        return String.valueOf((char) decimal);
    }

    public static String htmlSpecialChars(String s) {
        return regexReplace(P_RIGHT_ARROW, "&gt;", regexReplace(P_LEFT_ARROW, "&lt;", regexReplace(P_QUOTE, "&quot;", regexReplace(P_AMP, "&amp;", s))));
    }

    public String filter(String input) {
        reset();
        debug("************************************************");
        debug("              INPUT: " + input);
        String s = escapeComments(input);
        debug("     escapeComments: " + s);
        String s2 = balanceHTML(s);
        debug("        balanceHTML: " + s2);
        String s3 = checkTags(s2);
        debug("          checkTags: " + s3);
        String s4 = processRemoveBlanks(s3);
        debug("processRemoveBlanks: " + s4);
        String s5 = validateEntities(s4);
        debug("    validateEntites: " + s5);
        debug("************************************************\n\n");
        return s5;
    }

    public boolean isAlwaysMakeTags() {
        return this.alwaysMakeTags;
    }

    public boolean isStripComments() {
        return this.stripComment;
    }

    private String escapeComments(String s) {
        Matcher m = P_COMMENTS.matcher(s);
        StringBuffer buf = new StringBuffer();
        if (m.find()) {
            m.appendReplacement(buf, Matcher.quoteReplacement("<!--" + htmlSpecialChars(m.group(1)) + "-->"));
        }
        m.appendTail(buf);
        return buf.toString();
    }

    private String balanceHTML(String s) {
        String s2;
        if (this.alwaysMakeTags) {
            s2 = regexReplace(P_XML_CONTENT, "$1<$2", regexReplace(P_BODY_TO_END, "<$1>", regexReplace(P_END_ARROW, StringPool.EMPTY, s)));
        } else {
            s2 = regexReplace(P_BOTH_ARROWS, StringPool.EMPTY, regexReplace(P_STRAY_RIGHT_ARROW, "$1$2&gt;<", regexReplace(P_STRAY_LEFT_ARROW, "&lt;$1", s)));
        }
        return s2;
    }

    private String checkTags(String s) {
        Matcher m = P_TAGS.matcher(s);
        StringBuffer buf = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(buf, Matcher.quoteReplacement(processTag(m.group(1))));
        }
        m.appendTail(buf);
        String s2 = buf.toString();
        for (String key : this.vTagCounts.keySet()) {
            for (int ii = 0; ii < this.vTagCounts.get(key).intValue(); ii++) {
                s2 = s2 + "</" + key + StringPool.RIGHT_CHEV;
            }
        }
        return s2;
    }

    private String processRemoveBlanks(String s) {
        String result = s;
        String[] strArr = this.vRemoveBlanks;
        for (String tag : strArr) {
            if (!P_REMOVE_PAIR_BLANKS.containsKey(tag)) {
                P_REMOVE_PAIR_BLANKS.putIfAbsent(tag, Pattern.compile(StringPool.LEFT_CHEV + tag + "(\\s[^>]*)?></" + tag + StringPool.RIGHT_CHEV));
            }
            String result2 = regexReplace(P_REMOVE_PAIR_BLANKS.get(tag), StringPool.EMPTY, result);
            if (!P_REMOVE_SELF_BLANKS.containsKey(tag)) {
                P_REMOVE_SELF_BLANKS.putIfAbsent(tag, Pattern.compile(StringPool.LEFT_CHEV + tag + "(\\s[^>]*)?/>"));
            }
            result = regexReplace(P_REMOVE_SELF_BLANKS.get(tag), StringPool.EMPTY, result2);
        }
        return result;
    }

    private static String regexReplace(Pattern regexPattern, String replacement, String s) {
        return regexPattern.matcher(s).replaceAll(replacement);
    }

    private String processTag(String s) {
        Matcher m = P_END_TAG.matcher(s);
        if (m.find()) {
            String name = m.group(1).toLowerCase();
            if (allowed(name) && !inArray(name, this.vSelfClosingTags) && this.vTagCounts.containsKey(name)) {
                this.vTagCounts.put(name, Integer.valueOf(this.vTagCounts.get(name).intValue() - 1));
                return "</" + name + StringPool.RIGHT_CHEV;
            }
        }
        Matcher m2 = P_START_TAG.matcher(s);
        if (m2.find()) {
            String name2 = m2.group(1).toLowerCase();
            String body = m2.group(2);
            String ending = m2.group(3);
            if (!allowed(name2)) {
                return StringPool.EMPTY;
            }
            String params = StringPool.EMPTY;
            Matcher m22 = P_QUOTED_ATTRIBUTES.matcher(body);
            Matcher m3 = P_UNQUOTED_ATTRIBUTES.matcher(body);
            List<String> paramNames = new ArrayList<>();
            List<String> paramValues = new ArrayList<>();
            while (m22.find()) {
                paramNames.add(m22.group(1));
                paramValues.add(m22.group(3));
            }
            while (m3.find()) {
                paramNames.add(m3.group(1));
                paramValues.add(m3.group(3));
            }
            for (int ii = 0; ii < paramNames.size(); ii++) {
                String paramName = paramNames.get(ii).toLowerCase();
                String paramValue = paramValues.get(ii);
                if (allowedAttribute(name2, paramName)) {
                    if (inArray(paramName, this.vProtocolAtts)) {
                        paramValue = processParamProtocol(paramValue);
                    }
                    params = params + StringPool.SPACE + paramName + "=\"" + paramValue + StringPool.QUOTE;
                }
            }
            if (inArray(name2, this.vSelfClosingTags)) {
                ending = " /";
            }
            if (inArray(name2, this.vNeedClosingTags)) {
                ending = StringPool.EMPTY;
            }
            if (ending != null && ending.length() >= 1) {
                ending = " /";
            } else if (this.vTagCounts.containsKey(name2)) {
                this.vTagCounts.put(name2, Integer.valueOf(this.vTagCounts.get(name2).intValue() + 1));
            } else {
                this.vTagCounts.put(name2, 1);
            }
            return StringPool.LEFT_CHEV + name2 + params + ending + StringPool.RIGHT_CHEV;
        }
        Matcher m4 = P_COMMENT.matcher(s);
        if (this.stripComment || !m4.find()) {
            return StringPool.EMPTY;
        }
        return StringPool.LEFT_CHEV + m4.group() + StringPool.RIGHT_CHEV;
    }

    private String processParamProtocol(String s) {
        String s2 = decodeEntities(s);
        Matcher m = P_PROTOCOL.matcher(s2);
        if (m.find()) {
            String protocol = m.group(1);
            if (!inArray(protocol, this.vAllowedProtocols)) {
                s2 = StringPool.HASH + s2.substring(protocol.length() + 1);
                if (s2.startsWith(StringPool.DOUBLE_SLASH)) {
                    s2 = StringPool.HASH + s2.substring(3);
                }
            }
        }
        return s2;
    }

    private String decodeEntities(String s) {
        StringBuffer buf = new StringBuffer();
        Matcher m = P_ENTITY.matcher(s);
        while (m.find()) {
            m.appendReplacement(buf, Matcher.quoteReplacement(chr(Integer.decode(m.group(1)).intValue())));
        }
        m.appendTail(buf);
        String s2 = buf.toString();
        StringBuffer buf2 = new StringBuffer();
        Matcher m2 = P_ENTITY_UNICODE.matcher(s2);
        while (m2.find()) {
            m2.appendReplacement(buf2, Matcher.quoteReplacement(chr(Integer.valueOf(m2.group(1), 16).intValue())));
        }
        m2.appendTail(buf2);
        String s3 = buf2.toString();
        StringBuffer buf3 = new StringBuffer();
        Matcher m3 = P_ENCODE.matcher(s3);
        while (m3.find()) {
            m3.appendReplacement(buf3, Matcher.quoteReplacement(chr(Integer.valueOf(m3.group(1), 16).intValue())));
        }
        m3.appendTail(buf3);
        return validateEntities(buf3.toString());
    }

    private String validateEntities(String s) {
        StringBuffer buf = new StringBuffer();
        Matcher m = P_VALID_ENTITIES.matcher(s);
        while (m.find()) {
            m.appendReplacement(buf, Matcher.quoteReplacement(checkEntity(m.group(1), m.group(2))));
        }
        m.appendTail(buf);
        return encodeQuotes(buf.toString());
    }

    private String encodeQuotes(String s) {
        if (!this.encodeQuotes) {
            return s;
        }
        StringBuffer buf = new StringBuffer();
        Matcher m = P_VALID_QUOTES.matcher(s);
        while (m.find()) {
            String one = m.group(1);
            String two = m.group(2);
            m.appendReplacement(buf, Matcher.quoteReplacement(one + regexReplace(P_QUOTE, "&quot;", two) + m.group(3)));
        }
        m.appendTail(buf);
        return buf.toString();
    }

    private String checkEntity(String preamble, String term) {
        return (!StringPool.SEMICOLON.equals(term) || !isValidEntity(preamble)) ? "&amp;" + preamble : '&' + preamble;
    }

    private boolean isValidEntity(String entity) {
        return inArray(entity, this.vAllowedEntities);
    }

    private static boolean inArray(String s, String[] array) {
        for (String item : array) {
            if (item != null && item.equals(s)) {
                return true;
            }
        }
        return false;
    }

    private boolean allowed(String name) {
        return (this.vAllowed.isEmpty() || this.vAllowed.containsKey(name)) && !inArray(name, this.vDisallowed);
    }

    private boolean allowedAttribute(String name, String paramName) {
        return allowed(name) && (this.vAllowed.isEmpty() || this.vAllowed.get(name).contains(paramName));
    }
}
