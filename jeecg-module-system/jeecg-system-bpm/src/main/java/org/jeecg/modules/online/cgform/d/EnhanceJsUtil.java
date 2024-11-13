package org.jeecg.modules.online.cgform.d;

import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.entity.OnlCgformButton;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceJs;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.enums.CgformConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: EnhanceJsUtil.java */
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgform/d/d.class */
public class EnhanceJsUtil {
    private static final Logger a = LoggerFactory.getLogger(EnhanceJsUtil.class);
    private static final String strb = "beforeSubmit,beforeAdd,beforeEdit,afterAdd,afterEdit,beforeDelete,afterDelete,mounted,created,show,loaded";
    private static final String c = "\\}\\s*\r*\n*\\s*";
    private static final String d = ",";

    public static String a(String str, String str2) {
        String str3;
        String str4 = "(" + str2 + "\\s*\\(row\\)\\s*\\{)";
        String str5 = str2 + ":function(that,row){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;";
        String b2 = b(str, c + str4, "}," + str5);
        // FIXME 如果增强的类型是class对象，就不需要在各个方法中加逗号，但是改造后前端改动量很大，如bind指向会有很多问题
        // String str5 = str2 + "(that,row){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;";
        // String b2 = b(str, c + str4, "}" + str5);
        if (b2 == null) {
            str3 = c(str, str4, str5);
        } else {
            str3 = b2;
        }
        return a(str3, str2, (String) null);
    }

    public static String a(String str, String str2, String str3) {
        String str4;
        String str5 = "(" + oConvertUtils.getString(str3) + str2 + "\\s*\\(\\)\\s*\\{)";
        String str6 = str2 + ":function(that){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;";
        String b2 = b(str, c + str5, "}," + str6);
        // FIXME 如果增强的类型是class对象，就不需要在各个方法中加逗号，但是改造后前端改动量很大，如bind指向会有很多问题
        // String str6 = str2 + "(that){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;";
        // String b2 = b(str, c + str5, "}" + str6);
        if (b2 == null) {
            str4 = c(str, str5, str6);
        } else {
            str4 = b2;
        }
        return str4;
    }

    public static String b(String str, String str2, String str3) {
        Matcher matcher = Pattern.compile(str2).matcher(str);
        if (matcher.find()) {
            return str.replace(matcher.group(0), str3);
        }
        return null;
    }

    public static String c(String str, String str2, String str3) {
        String b2 = b(str, str2, str3);
        if (b2 != null) {
            return b2;
        }
        return str;
    }

    public static String a(String str, List<OnlCgformButton> list) {
        a.info("最终的增强JS: {}", str);
        return "class OnlineEnhanceJs{constructor(getAction,postAction,deleteAction){this._getAction=getAction;this._postAction=postAction;this._deleteAction=deleteAction;}" + str + "}";
    }

    public static String b(String str, String str2) {
        String str3;
        String str4 = "(\\s+" + str2 + "\\s*\\(\\)\\s*\\{)";
        String str5 = str2 + ":function(that,event){";
        String b2 = b(str, c + str4, "}," + str5);
        // FIXME 如果增强的类型是class对象，就不需要在各个方法中加逗号，但是改造后前端改动量很大，如bind指向会有很多问题
        // String str5 = str2 + "(that,event){";
        // String b2 = b(str, c + str4, "}" + str5);
        if (b2 == null) {
            str3 = c(str, str4, str5);
        } else {
            str3 = b2;
        }
        return str3;
    }

    public static String a(String str) {
        String str2 = "function OnlineEnhanceJs(getAction,postAction,deleteAction){return {_getAction:getAction,_postAction:postAction,_deleteAction:deleteAction," + str + "}}";
        // FIXME 如果增强的类型是class对象，就不需要在各个方法中加逗号，但是改造后前端改动量很大，如bind指向会有很多问题
        // String str2 = "class OnlineEnhanceJs{constructor(getAction,postAction,deleteAction){this._getAction=getAction;this._postAction=postAction;this._deleteAction=deleteAction;}" + str + "}";
        a.info("最终的增强JS: {}", str2);
        return str2;
    }

    public static String b(String str, List<OnlCgformButton> list) {
        String str2 = "function OnlineEnhanceJs(getAction,postAction,deleteAction){return {_getAction:getAction,_postAction:postAction,_deleteAction:deleteAction," + c(str, list) + "}}";
        // FIXME 如果增强的类型是class对象，就不需要在各个方法中加逗号，但是改造后前端改动量很大，如bind指向会有很多问题
        // String str2 = "class OnlineEnhanceJs{constructor(getAction,postAction,deleteAction){this._getAction=getAction;this._postAction=postAction;this._deleteAction=deleteAction;}" + c(str, list) + "}";
        a.info("最终的增强JS: {}", str2);
        return str2;
    }

    public static String c(String str, List<OnlCgformButton> list) {
        String[] split;
        String a2;
        if (list != null) {
            for (OnlCgformButton onlCgformButton : list) {
                String buttonCode = onlCgformButton.getButtonCode();
                if ("link".equals(onlCgformButton.getButtonStyle())) {
                    str = a(str, buttonCode);
                } else if ("button".equals(onlCgformButton.getButtonStyle()) || b.FORM.equals(onlCgformButton.getButtonStyle())) {
                    str = a(str, buttonCode, (String) null);
                }
            }
        }
        for (String str2 : strb.split(",")) {
            if ("beforeAdd,afterAdd,mounted,created,show,loaded".contains(str2)) {
                a2 = a(str, str2, (String) null);
            } else {
                a2 = a(str, str2);
            }
            str = a2;
        }
        return str;
    }

    public static void a(OnlCgformEnhanceJs onlCgformEnhanceJs, String str, List<OnlCgformField> list) {
        if (onlCgformEnhanceJs == null || oConvertUtils.isEmpty(onlCgformEnhanceJs.getCgJs())) {
            return;
        }
        String str2 = " " + onlCgformEnhanceJs.getCgJs();
        a.debug("one enhanceJs begin==> " + str2);
        if (Pattern.compile("(\\s{1}onlChange\\s*\\(\\)\\s*\\{)").matcher(str2).find()) {
            a.debug("---JS 增强转换-main--enhanceJsFunctionName----onlChange");
            str2 = a(str2, CgformConstant.ONLINE_JS_CHANGE_FUNCTION_NAME, "\\s{1}");
            for (OnlCgformField onlCgformField : list) {
                str2 = b(str2, onlCgformField.getDbFieldName());
            }
        }
        a.debug("one enhanceJs end==> " + str2);
        onlCgformEnhanceJs.setCgJs(str2);
    }

    public static void b(OnlCgformEnhanceJs onlCgformEnhanceJs, String str, List<OnlCgformField> list) {
        if (onlCgformEnhanceJs == null || oConvertUtils.isEmpty(onlCgformEnhanceJs.getCgJs())) {
            return;
        }
        a.info(" sub enhanceJs begin==> " + onlCgformEnhanceJs);
        String cgJs = onlCgformEnhanceJs.getCgJs();
        String str2 = str + "_" + CgformConstant.ONLINE_JS_CHANGE_FUNCTION_NAME;
        if (Pattern.compile("(" + str2 + "\\s*\\(\\)\\s*\\{)").matcher(cgJs).find()) {
            a.info("---JS 增强转换-sub-- enhanceJsFunctionName----" + str2);
            cgJs = a(cgJs, str2, (String) null);
            for (OnlCgformField onlCgformField : list) {
                cgJs = b(cgJs, onlCgformField.getDbFieldName());
            }
        }
        a.info(" sub enhanceJs end==> " + cgJs);
        onlCgformEnhanceJs.setCgJs(cgJs);
    }
}
