package org.jeecg.modules.online.cgform.enhance.a;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaInter;
import org.jeecg.modules.online.config.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/* compiled from: CgformEnhanceBeanDemo.java */
@Component("cgformEnhanceBeanDemo")
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgform/enhance/a/a.class */
public class a implements CgformEnhanceJavaInter {
    private static final Logger a = LoggerFactory.getLogger(a.class);

    @Override // org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaInter
    public int execute(String tableName, JSONObject json) throws BusinessException {
        a.info("--------我是自定义java增强测试bean-----------");
        a.info("--------当前表单 tableName=> " + tableName);
        a.info("--------当前表单 JSON数据=> " + json.toJSONString());
        if (json.containsKey("phone")) {
            json.put("phone", "18611100000");
            return 1;
        }
        return 1;
    }

    @Override // org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaInter
    public int execute(String tableName, Map<String, Object> map) throws BusinessException {
        return 1;
    }
}
