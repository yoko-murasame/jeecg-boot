package org.jeecg.common.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import org.springframework.util.AntPathMatcher;

public class PathMatcherUtil {
    public static String[] SIGN_URL_LIST = new String[]{"/sys/dict/getDictItems/*", "/sys/dict/loadDict/*", "/sys/dict/loadDictOrderByValue/*", "/sys/dict/loadDictItem/*", "/sys/dict/loadTreeData", "/sys/api/queryTableDictItemsByCode", "/sys/api/queryFilterTableDictInfo", "/sys/api/queryTableDictByKeys", "/sys/api/translateDictFromTable", "/sys/api/translateDictFromTableByKeys"};

    public PathMatcherUtil() {
    }

    public static void main(String[] args) {
        String url = "/sys/dict/loadDictOrderByValue/tree,s2,2";
        String p = "/sys/dict/loadDictOrderByValue/*";
        System.out.println(match(p, url));
    }

    public static boolean match(String matchPath, String path) {
        SpringAntMatcher springAntMatcher = new SpringAntMatcher(matchPath, true);
        return springAntMatcher.matches(path);
    }

    public static boolean matches(Collection<String> list, String path) {
        Iterator var2 = list.iterator();

        SpringAntMatcher springAntMatcher;
        do {
            if (!var2.hasNext()) {
                return false;
            }

            String s = (String)var2.next();
            springAntMatcher = new SpringAntMatcher(s, true);
        } while(!springAntMatcher.matches(path));

        return true;
    }

    private interface Matcher {
        boolean matches(String var1);

        Map<String, String> extractUriTemplateVariables(String var1);
    }

    private static class SpringAntMatcher implements Matcher {
        private final AntPathMatcher antMatcher;
        private final String pattern;

        private SpringAntMatcher(String pattern, boolean caseSensitive) {
            this.pattern = pattern;
            this.antMatcher = createMatcher(caseSensitive);
        }

        public boolean matches(String path) {
            return this.antMatcher.match(this.pattern, path);
        }

        public Map<String, String> extractUriTemplateVariables(String path) {
            return this.antMatcher.extractUriTemplateVariables(this.pattern, path);
        }

        private static AntPathMatcher createMatcher(boolean caseSensitive) {
            AntPathMatcher matcher = new AntPathMatcher();
            matcher.setTrimTokens(false);
            matcher.setCaseSensitive(caseSensitive);
            return matcher;
        }
    }
}
