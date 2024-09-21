package org.jeecg.modules.online.cgform.enhance.a;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaInter;
import org.jeecg.modules.online.config.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/* compiled from: CgformEnhanceJavaDemo.java */
@Component("cgformEnhanceJavaDemo")
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgform/enhance/a/d.class */
public class d implements CgformEnhanceJavaInter {
    private static final Logger a = LoggerFactory.getLogger(d.class);

    @Override // org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaInter
    public int execute(String tableName, JSONObject json) throws BusinessException {
        a.info("----------我是自定义java增强测试demo类-----------");
        a.info("--------当前tableName=>" + tableName);
        a.info("--------当前JSON数据=>" + json.toJSONString());
        return 1;
    }

    @Override // org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaInter
    public int execute(String tableName, Map<String, Object> map) throws BusinessException {
        return 1;
    }
}
