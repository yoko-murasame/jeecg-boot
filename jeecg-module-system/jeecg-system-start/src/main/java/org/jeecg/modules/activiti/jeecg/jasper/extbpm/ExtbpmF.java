package org.jeecg.modules.activiti.jeecg.jasper.extbpm;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.net.URLDecoder;
import java.util.*;
import java.util.Map.Entry;

public abstract class ExtbpmF {
    public ExtbpmF() {
    }

    public static String a(Map<String, String> var0, String var1) {
        if (var1 == null) {
            throw new RuntimeException("key不能为空");
        } else {
            String var2 = b(var0, var1);
            return var2;
        }
    }

    private static String b(Map<String, String> var0, String var1) {
        StringBuffer var2 = new StringBuffer();
        TreeMap var3 = new TreeMap(var0);
        Set var4 = var3.entrySet();
        Iterator var5 = var4.iterator();

        while(var5.hasNext()) {
            Entry var6 = (Entry)var5.next();
            String var7 = (String)var6.getKey();
            String var8 = (String)var6.getValue();
            if (null != var8 && !"".equals(var8) && !"null".equals(var8) && !"sign".equals(var7) && !"key".equals(var7)) {
                var2.append(var7 + "=" + var8 + "&");
            }
        }

        var2.append("key=" + var1);
        String var9 = ExtbpmC.a(var2.toString(), "UTF-8").toUpperCase();
        return var9;
    }

    public static boolean a(Map<String, String> var0, String var1, String var2) {
        if (var1 == null) {
            throw new RuntimeException("key不能为空");
        } else if (var2 == null) {
            throw new RuntimeException("需要验签的字符为空");
        } else {
            return var2.equals(a(var0, var1));
        }
    }

    public static Map<String, String> a(String var0) {
        HashMap var1 = new HashMap();
        var0 = var0.substring(var0.indexOf("?") + 1);
        String[] var2 = var0.split("&");

        for(int var3 = 0; var3 < var2.length; ++var3) {
            String var4 = var2[var3];
            if (var4.indexOf("=") != -1) {
                String[] var5 = var4.split("=");
                if (var5 != null && var5.length == 2) {
                    if ("nickname".equals(var5[0])) {
                        var1.put(var5[0], URLDecoder.decode(var5[1]));
                    } else {
                        var1.put(var5[0], var5[1]);
                    }
                }
            }
        }

        return var1;
    }

    public static String a() {
        // String var0 = "26F727A80372E84B6CFAEDD6F7B19139CC47B1912B6CAE53";
        // String var1 = "kqmCG6eh8Oce2d3xvFA4pOOA87YEh6pSU3vnXl5RXgtZX6paTjOuN3pAnRS6TqQYeGYaEdQ3hlnqERGy6J4XkxzxyhWGnMHyGTYkmBJjUDFdchYbwyHo9K+e8VhrYVqFBv782dIo8d18g/fOB9wiho4QVT2x2UnMRcbMlos1UVE=";
        // String var2 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCxHEhhLwdDhZ57SlLt/5JWFeGRy4r+NKgbA0VwkCXs7p0w1bNgKlRJOzUfjg6kMi4ygkVNNPWrXIhcdMniTbcHmawawLTa3cRM1jNdG0xg808KKNVdDilFHOFQz8AF6cvLDpsWkqzmoe2+64v0zlWbp5EpYxMMRxOwSMVKxLB8BwIDAQAB";
        // String var3 = null;
        // String var4 = org.jeecg.designer.commons.c.c();
        // String var5 = org.jeecg.designer.commons.c.f();
        // String var6 = "";
        // JSONObject var7 = new JSONObject();
        // var7.put("sn", var4);
        // var7.put("orgcode", var5);
        // String var8 = var7.toJSONString();
        // HashMap var9 = new HashMap();
        // var9.put("body", var8);
        // String var10 = a(var9, "26F727A80372E84B6CFAEDD6F7B19139CC47B1912B6CAE53");
        // byte[] var11 = ExtbpmD.a("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCxHEhhLwdDhZ57SlLt/5JWFeGRy4r+NKgbA0VwkCXs7p0w1bNgKlRJOzUfjg6kMi4ygkVNNPWrXIhcdMniTbcHmawawLTa3cRM1jNdG0xg808KKNVdDilFHOFQz8AF6cvLDpsWkqzmoe2+64v0zlWbp5EpYxMMRxOwSMVKxLB8BwIDAQAB", "kqmCG6eh8Oce2d3xvFA4pOOA87YEh6pSU3vnXl5RXgtZX6paTjOuN3pAnRS6TqQYeGYaEdQ3hlnqERGy6J4XkxzxyhWGnMHyGTYkmBJjUDFdchYbwyHo9K+e8VhrYVqFBv782dIo8d18g/fOB9wiho4QVT2x2UnMRcbMlos1UVE=");
        //
        // try {
        //     var3 = new String(var11, "UTF-8");
        // } catch (UnsupportedEncodingException var14) {
        //     var14.printStackTrace();
        // }
        //
        // JSONObject var12 = ExtbpmB.a(var3, "POST", "body=" + var8, var10);
        // if (var12 != null) {
        //     if ("true".equals(var12.getString("success"))) {
        //         var6 = var12.getString("license");
        //     } else {
        //         String var13 = var12.getString("msg");
        //         System.err.println("生成失败=>" + var13);
        //     }
        // } else {
        //     System.err.println("生成失败=>服务异常，请稍候重试");
        // }
        //
        // return var6;
        return "";
    }
}
