package org.jeecg.modules.system.rule;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.handler.IFillRuleHandler;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 填值规则-通用模块编号生成
 * 规则Code：module_code_rule
 * 设置参数：{"tableName":"表名","fieldName":"编号字段名","prefix":"前缀"}
 * 前缀规则：传入前缀+YYMMdd+三位数顺序号，如：XX2312200001
 */
@Component
public class ModuleCodeRule implements IFillRuleHandler {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public Object execute(JSONObject params, JSONObject formData) {

        String tableName = params.getString("tableName");
        Assert.hasText(tableName, "参数[tableName]不能为空");
        String fieldName = params.getString("fieldName");
        Assert.hasText(fieldName, "参数[fieldName]不能为空");
        String prefix = params.getString("prefix");
        Assert.hasText(prefix, "参数[prefix]不能为空");

        // 获取YYMMdd
        SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
        String date = format.format(new Date());
        prefix += date;

        // 获取三位数顺序号
        String sql = "select " + fieldName + " from " + tableName
                + " where " + fieldName + " like '" + prefix + "%'"
                + " order by " + fieldName + " desc limit 1";
        try {
            String latestOrder = jdbcTemplate.queryForObject(sql, String.class);
            String codeNum = latestOrder.substring(prefix.length());
            int num = Integer.parseInt(codeNum) + 1;
            String code = String.format("%03d", num);
            return prefix + code;
        } catch (Exception e) {
            // 生成新的编号
            return prefix + "001";
        }
    }

}
